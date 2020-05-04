package sales.batch.watcher.model;

public class Salesman {
    private final String name;
    private final String cpf;
    private final double salary;

    public Salesman(String name, String cpf, double salary) {
        this.name = name;
        this.cpf = cpf;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Salesman{" +
            "name='" + name + '\'' +
            ", cpf='" + cpf + '\'' +
            ", salary=" + salary +
            '}';
    }
}
