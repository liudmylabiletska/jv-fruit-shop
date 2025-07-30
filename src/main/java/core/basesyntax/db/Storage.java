package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, Integer> fruitStorage = new HashMap<>();

    private Storage() {
    }

    public static Map<String, Integer> getFruitStorage() {
        return fruitStorage;
    }

    public static void clear() {
        fruitStorage.clear();
    }
}
