package sales.batch.watcher.persistence.dao.strategy;

import sales.batch.watcher.model.Salesman;

import java.util.List;

public class SalesmanStrategy implements Strategy<Salesman> {
    @Override
    public Salesman create(List<String> tokens) {
        String cpf = tokens.get(1);
        String name = tokens.get(2);
        double salary = Double.parseDouble(tokens.get(3));
        return new Salesman(name, cpf, salary);
    }
}
