package sales.batch.watcher;

import sales.batch.watcher.business.SaleHistoryReport;
import sales.batch.watcher.dao.DirectoryUtils;
import sales.batch.watcher.dao.SaleHistoryDAO;
import sales.batch.watcher.model.SaleHistory;

import java.util.List;

public class App {

    public static void main(String[] args) {
        App.loadInputFiles();
        App.watchNewFiles();
    }

    private static void watchNewFiles() {
        // TODO: implementation
    }

    private static void loadInputFiles() {
        List<String> inputFilesPaths = DirectoryUtils.listFilesInDir(DirectoryUtils.INPUT_PATH);
        App.processUnprocessedFiles(inputFilesPaths);
    }

    private static void processUnprocessedFiles(List<String> inputFilesPaths) {
        List<String> unprocessdFiles = DirectoryUtils.filterUnprocessedFiles(inputFilesPaths);
        unprocessdFiles.forEach(App::processFile);
    }

    private static void processFile(String filePath) {
        String outputFilePath = DirectoryUtils.renameInputPathToOutput(filePath);

        System.out.println("INPUT FILE NAME: " + filePath);
        System.out.println("OUTPUT FILE NAME: " + outputFilePath);

        SaleHistoryDAO loader = new SaleHistoryDAO();
        SaleHistory history = loader.process(filePath);

        List<String> lines = SaleHistoryReport.generateReportLines(history);
        lines.forEach(System.out::println);

        DirectoryUtils.writeLinesToFile(outputFilePath, lines);

        System.out.println();
    }
}
