package utils;

import model.exception.MyException;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{
    private Map<Integer, Value> map;
    private static Integer freeValue;

    public MyHeap(Map<Integer, Value> map) {
        this.map = map;
        freeValue = 0;
    }

    public MyHeap() {
        map = new HashMap<>();
        freeValue = 0;
    }

    public boolean containsKey(Integer address){
        return map.containsKey(address);
    }

    @Override
    public Integer getFreeValue() {
        return freeValue;
    }

    @Override
    public Map<Integer, Value> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer, Value> newMap) {
        map.clear();
        for (Integer i : newMap.keySet()) {
            map.put(i, newMap.get(i));
        }
    }

    @Override
    public Integer add(Value value) {
        freeValue++;
        map.put(freeValue, value);
        return freeValue;
    }

    @Override
    public void update(Integer position, Value value) throws MyException {
        if (!map.containsKey(position))
            throw new MyException(String.format("ERROR: %d is not present in the heap", position));
        map.put(position, value);
    }

    @Override
    public Value get(Integer position) throws MyException {
        if (!map.containsKey(position))
            throw new MyException(String.format("ERROR: %s not present in the heap", position));
        return map.get(position);
    }

    @Override
    public String toString(){
        return "MyHeap{" +
                "heap=" + map + '}';
    }
}
