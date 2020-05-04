package sales.batch.watcher;

import sales.batch.watcher.dao.SaleHistoryDAO;
import sales.batch.watcher.model.Client;
import sales.batch.watcher.model.Sale;
import sales.batch.watcher.model.SaleHistory;
import sales.batch.watcher.model.Salesman;

public class App {

    public static void main(String[] args) {
        String fileName = "0.txt";
        String filePath = System.getenv("HOME") + "/data/in/" + fileName;
        SaleHistoryDAO loader = new SaleHistoryDAO();
        SaleHistory history = loader.process(filePath);

        for (Salesman salesman : history.getSalesmen()) {
            System.out.println(salesman);
        }

        for (Client client : history.getClients()) {
            System.out.println(client);
        }

        for (Sale sale : history.getSales()) {
            System.out.println(sale);
        }
    }
}
