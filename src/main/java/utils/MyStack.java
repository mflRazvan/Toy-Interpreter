package utils;

import model.exception.MyException;

import java.util.*;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }
    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List toList(){return new ArrayList(stack);}

    @Override
    public T peek() throws MyException {
        if (isEmpty()) {
            throw new MyException("Stack is empty");
        }
        return stack.peek();
    }

    @Override
    public List<T> getReverse() {
        List<T> auxList = Arrays.asList((T[])stack.toArray());
        Collections.reverse(auxList);
        return auxList;
    }

    @Override
    public String toString() {
        return "stack " + getReverse();
    }
}
