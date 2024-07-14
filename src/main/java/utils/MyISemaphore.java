package utils;

import javafx.util.Pair;
import model.exception.MyException;
import model.value.Value;

import java.util.HashMap;
import java.util.List;

public interface MyISemaphore {
    void put(int key, Pair<Integer, List<Integer>> value) throws MyException;

    Pair<Integer, List<Integer>> get(int key) throws MyException;
    boolean containsKey(int key);
    int getFreeAddress();
    void setFreeAddress(int freeAddress);
    void update(int key, Pair<Integer, List<Integer>> value) throws MyException;
    HashMap<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable();
    void setSemaphoreTable(HashMap<Integer, Pair<Integer, List<Integer>>> newSemaphoreTable);
}
