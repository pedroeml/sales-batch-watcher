package sales.batch.watcher.model;

public class Client {
    private final String name;
    private final String businessArea;
    private final String cnpj;

    public Client(String name, String businessArea, String cnpj) {
        this.name = name;
        this.businessArea = businessArea;
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public String getCnpj() {
        return cnpj;
    }

    @Override
    public String toString() {
        return "Client{" +
            "name='" + name + '\'' +
            ", businessArea='" + businessArea + '\'' +
            ", cnpj='" + cnpj + '\'' +
            '}';
    }
}
