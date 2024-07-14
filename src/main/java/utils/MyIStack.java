package utils;

import model.exception.MyException;

import java.util.List;

public interface MyIStack<T> {
    void push(T elem);
    T pop();
    boolean isEmpty();

    List toList();

    T peek() throws MyException;

    List<T> getReverse();
}
