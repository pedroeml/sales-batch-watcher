package sales.batch.watcher.business;

import sales.batch.watcher.model.Item;
import sales.batch.watcher.model.Sale;
import sales.batch.watcher.model.SaleHistory;
import sales.batch.watcher.model.Salesman;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleHistoryAnalysis {

    public static int salesmenQuantity(SaleHistory history) {
        return history.getSalesmen().size();
    }

    public static int clientsQuantity(SaleHistory history) {
        return history.getClients().size();
    }

    public static Sale mostExpensiveSale(SaleHistory history) {
        Collection<Sale> sales = history.getSales();
        Sale mostExpensive = null;
        double mostExpensiveTotal = 0.0;

        for (Sale sale : sales) {
            double saleTotal = SaleHistoryAnalysis.calculateTotal(sale);
            if (saleTotal > mostExpensiveTotal) {
                mostExpensive = sale;
                mostExpensiveTotal = saleTotal;
            }
        };

        return mostExpensive;
    }

    private static double calculateTotal(Sale sale) {
        List<Item> items = sale.getItems();

        return items.stream()
            .map((Item item) -> item.getPrice() * item.getQuantity())
            .reduce(0.0, Double::sum);
    }

    public static Salesman worstSalesman(SaleHistory history) {
        HashMap<String, Double> salesmenTotals = SaleHistoryAnalysis.calculateSalesmenTotals(history);
        String worstSalesmanCpf = null;
        Double worstSalesmanTotal = null;

        for (Map.Entry<String, Double> entry : salesmenTotals.entrySet()) {
            String cpf = entry.getKey();
            Double total = entry.getValue();

            if (worstSalesmanCpf == null && worstSalesmanTotal == null || total < worstSalesmanTotal) {
                worstSalesmanCpf = cpf;
                worstSalesmanTotal = total;
            }
        }

        return history.findSalesmanByCpf(worstSalesmanCpf);
    }

    private static HashMap<String, Double> calculateSalesmenTotals(SaleHistory history) {
        HashMap<String, Double> salesmenTotals = new HashMap<>();

        for (Salesman salesman : history.getSalesmen()) {
            salesmenTotals.put(salesman.getCpf(), 0.0);
        }

        for (Sale sale : history.getSales()) {
            Salesman salesman = sale.getSalesman();

            if (salesman != null) {
                double saleTotal = SaleHistoryAnalysis.calculateTotal(sale);
                salesmenTotals.computeIfPresent(salesman.getCpf(), (key, currentTotal) -> currentTotal + saleTotal);
            }
        }

        return salesmenTotals;
    }
}
