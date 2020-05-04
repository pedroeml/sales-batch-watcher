package sales.batch.watcher.dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryUtils {
    public static final String INPUT_PATH = System.getenv("HOME") + "/data/in/";
    public static final String OUTPUT_PATH = System.getenv("HOME") + "/data/out/";

    public static List<String> listFilesInDir(String dirPath) {
        List<String> filesPaths = Collections.emptyList();

        try (Stream<Path> walk = Files.walk(Paths.get(dirPath))) {
            filesPaths = walk.filter(Files::isRegularFile)
                .map(Path::toString)
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filesPaths;
    }

    public static void writeLinesToFile(String filePath, List<String> lines) {
        try {
            Files.write(Paths.get(filePath), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> filterUnprocessedFiles(List<String> inputFilesPaths) {
        return inputFilesPaths.stream()
            .filter((String filePath) -> {
                String outputFilePath = DirectoryUtils.renameInputPathToOutput(filePath);
                return DirectoryUtils.isFileNotPresent(outputFilePath);
            })
            .collect(Collectors.toList());
    }

    public static String renameInputPathToOutput(String inputFilePath) {
        return inputFilePath.replace(DirectoryUtils.INPUT_PATH, DirectoryUtils.OUTPUT_PATH);
    }

    public static boolean isFileNotPresent(String filePath) {
        return Files.notExists(Paths.get(filePath));
    }
}
