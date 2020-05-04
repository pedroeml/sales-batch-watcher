package sales.batch.watcher.persistence.dao;

import sales.batch.watcher.persistence.dao.factory.DomainModelFactory;
import sales.batch.watcher.model.SaleHistory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SaleHistoryDAO {
    private final DomainModelFactory factory;

    public SaleHistoryDAO() {
        this.factory = new DomainModelFactory();
    }

    public SaleHistory process(String filePath) {
        SaleHistory history = new SaleHistory();

        try (Stream<String> lines = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            lines.forEachOrdered((String line) -> {
                history.addDomainModelObject(parseLine(line));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        history.updateSales();
        return history;
    }

    public Object parseLine(String line) {
        List<String> tokens = Arrays.asList(line.split("ç"));

        if (tokens.size() != 4) {
            throw new Error("Each line is supposed to have 3 \"ç\" chars!");
        }

        return this.factory.create(tokens);
    }
}
