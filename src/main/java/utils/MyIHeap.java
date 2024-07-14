package utils;

import model.exception.MyException;
import model.value.Value;

import java.util.Map;

public interface MyIHeap {
    Integer getFreeValue();
    Map<Integer, Value> getContent();
    void setContent(Map<Integer, Value> newMap);
    Integer add(Value value);
    void update(Integer position, Value value) throws MyException;
    Value get(Integer position) throws MyException;
}
