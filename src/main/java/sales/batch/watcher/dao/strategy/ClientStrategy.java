package sales.batch.watcher.dao.strategy;

import sales.batch.watcher.model.Client;

import java.util.List;

public class ClientStrategy implements Strategy<Client> {
    @Override
    public Client create(List<String> tokens) {
        String cnpj = tokens.get(1);
        String name = tokens.get(2);
        String businessArea = tokens.get(3);
        return new Client(name, businessArea, cnpj);
    }
}
