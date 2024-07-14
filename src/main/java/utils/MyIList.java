package utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface MyIList<T> {
    void add(T itemToAdd);

    T get(int pos);

    void forEach(Consumer<T> action);

    public Stream<T> stream();

    public Integer size();

    public boolean addAll(Collection<? extends T> elements);

    public List getAll();

    public void clear();
}
