package sales.batch.watcher.dao.strategy;

import sales.batch.watcher.model.Item;
import sales.batch.watcher.model.Sale;
import sales.batch.watcher.model.Salesman;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaleStrategy implements Strategy<Sale> {
    private final Strategy<Item> strategy;

    public SaleStrategy(Strategy<Item> strategy) {
        this.strategy = strategy;
    }

    @Override
    public Sale create(List<String> tokens) {
        String id = tokens.get(1);
        String unparsedItemList = tokens.get(2);
        String salesmanName = tokens.get(3);
        List<Item> items = this.parseItems(unparsedItemList);
        return new Sale(id, items, salesmanName);
    }

    private List<Item> parseItems(String unparsedItemList) {
        String noBrackets = unparsedItemList.substring(1, unparsedItemList.length() - 1);
        List<String> unparsedItems = Arrays.asList(noBrackets.split(","));
        return unparsedItems.stream()
            .map((String unparsedItem) -> Arrays.asList(unparsedItem.split("-")))
            .map(this.strategy::create)
            .collect(Collectors.toList());
    }
}
