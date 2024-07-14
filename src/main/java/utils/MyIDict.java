package utils;

import java.util.Map;
import java.util.Set;

public interface MyIDict<K, V> {
    boolean isDefined(K key);

    void put(K key, V value);

    V lookUp(K key);

    Set<K> keySet();

    void update(K key, V value);

    void remove(K key);

    boolean containsKey(K key);

    Map<K, V> getContent();

    MyIDict<K, V> copy();
}
