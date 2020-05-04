package sales.batch.watcher.model;

import java.util.List;

public class Sale {
    private final String id;
    private final List<Item> items;
    private final String salesmanName;
    private Salesman salesman;

    public Sale(String id, List<Item> items, String salesmanName) {
        this.id = id;
        this.items = items;
        this.salesmanName = salesmanName;
    }

    public String getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        if (salesman != null && salesman.getName().equals(this.getSalesmanName())) {
            this.salesman = salesman;
        }
    }

    @Override
    public String toString() {
        return "Sale{" +
            "id='" + id + '\'' +
            ", items=" + items +
            ", salesman=" + salesman +
            '}';
    }
}
