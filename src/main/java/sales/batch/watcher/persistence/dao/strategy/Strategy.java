package sales.batch.watcher.persistence.dao.strategy;

import java.util.List;

public interface Strategy<T> {
    T create(List<String> tokens);
}
