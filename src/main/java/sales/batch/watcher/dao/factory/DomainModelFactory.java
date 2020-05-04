package sales.batch.watcher.dao.factory;

import sales.batch.watcher.dao.LineDataTypeEnum;
import sales.batch.watcher.dao.strategy.*;
import sales.batch.watcher.model.Client;
import sales.batch.watcher.model.Sale;
import sales.batch.watcher.model.Salesman;

import java.util.Arrays;
import java.util.List;

public class DomainModelFactory {
    private final Strategy<Salesman> salesmanStrategy;
    private final Strategy<Client> clientStrategy;
    private final Strategy<Sale> saleStrategy;

    public DomainModelFactory() {
        this.salesmanStrategy = new SalesmanStrategy();
        this.clientStrategy = new ClientStrategy();
        this.saleStrategy = new SaleStrategy(new ItemStrategy());
    }

    public Object create(List<String> tokens) {
        LineDataTypeEnum dataType = this.getMatchingType(tokens.get(0));

        return switch (dataType) {
            case SALESMAN -> this.salesmanStrategy.create(tokens);
            case CLIENT -> this.clientStrategy.create(tokens);
            case SALE -> this.saleStrategy.create(tokens);
            default -> null;
        };
    }

    private LineDataTypeEnum getMatchingType(String identifier) {
        List<LineDataTypeEnum> dataTypes = Arrays.asList(LineDataTypeEnum.values());
        LineDataTypeEnum dataType = LineDataTypeEnum.NONE;

        for (LineDataTypeEnum type : dataTypes) {
            if (type.getIdentifier().equals(identifier)) {
                dataType = type;
                break;
            }
        }

        return dataType;
    }
}
