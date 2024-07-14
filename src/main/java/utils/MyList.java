package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> items;

    public MyList()
    {
        items = new ArrayList<>();
    }

    public MyList(ArrayList<T> list){items = list;}

    @Override
    public T get(int pos){return items.get(pos);}

    @Override
    public void add(T itemToAdd) {
        items.add(itemToAdd);
    }

    @Override
    public void forEach(Consumer<T> action) {
        items.forEach(action::accept);
    }

    @Override
    public Stream<T> stream() {
        return items.stream();
    }

    @Override
    public Integer size(){
        return items.size();
    }

    @Override
    public boolean addAll(Collection<? extends T> elements) {
        return items.addAll(elements);
    }

    @Override
    public List getAll(){
        return this.items;
    }

    @Override
    public void clear(){
        items.clear();
    }

    @Override
    public String toString() {
        return "MyList{" +
                "items=" + items +
                '}';
    }
}
