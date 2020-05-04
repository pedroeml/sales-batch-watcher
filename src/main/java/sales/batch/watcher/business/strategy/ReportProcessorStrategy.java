package sales.batch.watcher.business.strategy;

import java.util.List;

public interface ReportProcessorStrategy {
    void processUnprocessedFiles(List<String> inputFilesPaths);
    void processFile(String filePath);
}
