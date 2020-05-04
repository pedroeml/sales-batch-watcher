package sales.batch.watcher.business.strategy;

import sales.batch.watcher.business.SaleHistoryReport;
import sales.batch.watcher.model.SaleHistory;
import sales.batch.watcher.persistence.dao.SaleHistoryDAO;
import sales.batch.watcher.persistence.utils.DirectoryUtils;

import java.util.List;

public class SaleHistoryReportProcessorStrategy implements ReportProcessorStrategy {

    @Override
    public void processUnprocessedFiles(List<String> inputFilesPaths) {
        List<String> unprocessdFiles = DirectoryUtils.filterUnprocessedFiles(inputFilesPaths);
        unprocessdFiles.forEach(this::processFile);
    }

    @Override
    public void processFile(String filePath) {
        String outputFilePath = DirectoryUtils.renameInputPathToOutput(filePath);

        System.out.println("INPUT FILE PATH: " + filePath);
        System.out.println("OUTPUT FILE PATH: " + outputFilePath);

        SaleHistoryDAO loader = new SaleHistoryDAO();
        SaleHistory history = loader.process(filePath);

        List<String> lines = SaleHistoryReport.generateReportLines(history);
        lines.forEach(System.out::println);

        DirectoryUtils.writeLinesToFile(outputFilePath, lines);

        System.out.println();
    }
}
