package sales.batch.watcher;

import sales.batch.watcher.business.strategy.ReportProcessorStrategy;
import sales.batch.watcher.business.strategy.SaleHistoryReportProcessorStrategy;
import sales.batch.watcher.persistence.utils.DirectoryUtils;
import sales.batch.watcher.persistence.utils.WatchDir;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class App {
    private final ReportProcessorStrategy strategy;

    public App() {
        this.strategy = new SaleHistoryReportProcessorStrategy();
    }

    public static void main(String[] args) {
        App app = new App();
        app.loadInputFiles();
        app.watchNewFiles();
    }

    private void watchNewFiles() {
        Path dir = Paths.get(DirectoryUtils.INPUT_PATH);
        try {
            WatchDir watch = new WatchDir(dir, this.strategy);
            watch.processQueuedEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInputFiles() {
        List<String> inputFilesPaths = DirectoryUtils.listFilesInDir(DirectoryUtils.INPUT_PATH);
        this.strategy.processUnprocessedFiles(inputFilesPaths);
    }
}
