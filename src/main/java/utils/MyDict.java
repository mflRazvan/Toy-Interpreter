package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDict<K,V> implements MyIDict<K,V>{

    Map<K,V> dictionary;
    public MyDict() {
        dictionary = new HashMap<>();
    }
    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public boolean containsKey(K key){
        return dictionary.containsKey(key);
    }

    @Override
    public void remove(K key){
        dictionary.remove(key);
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public V lookUp(K key) {
        return dictionary.get(key);
    }

    @Override
    public Set<K> keySet(){
        return dictionary.keySet();
    }

    @Override
    public void update(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public Map<K, V> getContent(){
        return dictionary;
    }

    @Override
    public MyIDict<K, V> copy(){
        MyIDict<K, V> toReturn = new MyDict<>();
        for (K key : keySet())
            toReturn.put(key, lookUp(key));
        return toReturn;
    }

    @Override
    public String toString() {
        return "MyDict{" +
                "dictionary=" + dictionary + '}';
    }
}
