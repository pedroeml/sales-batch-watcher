package sales.batch.watcher.model;

import java.util.Collection;
import java.util.HashMap;

public class SaleHistory {
    private final HashMap<String, Salesman> salesmen;
    private final HashMap<String, Client> clients;
    private final HashMap<String, Sale> sales;

    public SaleHistory() {
        this.salesmen = new HashMap<>();
        this.clients = new HashMap<>();
        this.sales = new HashMap<>();
    }

    public Collection<Salesman> getSalesmen() {
        return this.salesmen.values();
    }

    public Collection<Client> getClients() {
        return this.clients.values();
    }

    public Collection<Sale> getSales() {
        return this.sales.values();
    }

    public boolean addDomainModelObject(Object o) {
        if (o instanceof Salesman) {
            Salesman salesman = (Salesman) o;
            this.salesmen.put(salesman.getCpf(), salesman);
        } else if (o instanceof Client) {
            Client client = (Client) o;
            this.clients.put(client.getCnpj(), client);
        } else if (o instanceof Sale) {
            Sale sale = (Sale) o;
            this.sales.put(sale.getId(), sale);
        } else {
            return false;
        }

        return true;
    }

    public void updateSales() {
        Collection<Salesman> salesmen = this.getSalesmen();

        for (Sale sale : this.getSales()) {
            if (sale.getSalesman() == null) {
                Salesman salesman = this.findSalesmanByName(sale, salesmen);
                sale.setSalesman(salesman);
            }
        };
    }

    private Salesman findSalesmanByName(Sale sale, Collection<Salesman> salesmen) {
        for (Salesman salesman : salesmen) {
            if (sale.getSalesmanName().equals(salesman.getName())) {
                return salesman;
            }
        }

        return null;
    }
}
