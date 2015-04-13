package tc.app.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Simply a map wrapper - No Eviction Policy but can be added easily
 *
 * @param <K>
 * @param <V>
 */
public class Cache<K, V> {
    private final Map<K, V> _map = new HashMap<>();

    public void put(K k, V v) {
        _map.put(k, v);
    }

    public V get(K k) {
        return _map.get(k);
    }

    public boolean containsKey(K k) {
        return _map.containsKey(k);
    }

    public void remove(K k) {
        _map.remove(k);
    }
}
