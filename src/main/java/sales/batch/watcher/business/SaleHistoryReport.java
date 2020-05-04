package sales.batch.watcher.business;

import sales.batch.watcher.model.Sale;
import sales.batch.watcher.model.SaleHistory;
import sales.batch.watcher.model.Salesman;

import java.util.LinkedList;
import java.util.List;

public class SaleHistoryReport {

    public static List<String> generateReportLines(SaleHistory history) {
        int salesmenQuantity = SaleHistoryAnalysis.salesmenQuantity(history);
        int clientsQuantity = SaleHistoryAnalysis.clientsQuantity(history);
        Sale mostExpensiveSale = SaleHistoryAnalysis.mostExpensiveSale(history);
        Salesman worstSalesman = SaleHistoryAnalysis.worstSalesman(history);

        List<String> lines = new LinkedList<>();

        lines.add("Salesmen Quantity: " + salesmenQuantity);
        lines.add("Clients Quantity: " + clientsQuantity);
        if (mostExpensiveSale != null) {
            lines.add("Most Expensive Sale ID: " + mostExpensiveSale.getId());
        }

        if (salesmenQuantity > 0) {
            lines.add("Worst Salesman: " + worstSalesman);
        }

        return lines;
    }
}
