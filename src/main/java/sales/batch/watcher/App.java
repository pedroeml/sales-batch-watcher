package sales.batch.watcher;

import sales.batch.watcher.business.SaleHistoryAnalysis;
import sales.batch.watcher.dao.SaleHistoryDAO;
import sales.batch.watcher.model.Client;
import sales.batch.watcher.model.Sale;
import sales.batch.watcher.model.SaleHistory;
import sales.batch.watcher.model.Salesman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        String inputPath = System.getenv("HOME") + "/data/in/";
        String outputPath = System.getenv("HOME") + "/data/out/";

        try (Stream<Path> walk = Files.walk(Paths.get(inputPath))) {
            List<String> inputFilesPaths = walk.filter(Files::isRegularFile)
                .map(x -> x.toString())
                .collect(Collectors.toList());

            inputFilesPaths.forEach((String filePath) -> {
                System.out.println("FILE NAME: " + filePath);
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

                System.out.println("Salesmen Quantity: " + SaleHistoryAnalysis.salesmenQuantity(history));
                System.out.println("Clients Quantity: " + SaleHistoryAnalysis.clientsQuantity(history));
                System.out.println("Most Expensive Sale: " + SaleHistoryAnalysis.mostExpensiveSale(history));
                System.out.println("Worst Salesman: " + SaleHistoryAnalysis.worstSalesman(history));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
