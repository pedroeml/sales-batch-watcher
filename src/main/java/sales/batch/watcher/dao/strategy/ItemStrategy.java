package sales.batch.watcher.dao.strategy;

import sales.batch.watcher.model.Item;

import java.util.List;

public class ItemStrategy implements Strategy<Item> {
    @Override
    public Item create(List<String> tokens) {
        String id = tokens.get(0);
        int quantity = Integer.parseInt(tokens.get(1));
        double price = Double.parseDouble(tokens.get(2));
        return new Item(id, quantity, price);
    }
}
