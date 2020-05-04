package sales.batch.watcher.dao;

public enum LineDataTypeEnum {
    CLIENT("002"),
    SALESMAN("001"),
    SALE("003"),
    NONE("");

    private final String identifier;

    LineDataTypeEnum(String identifier) {
        this.identifier = identifier;
    }

    public final String getIdentifier() {
        return this.identifier;
    }
}
