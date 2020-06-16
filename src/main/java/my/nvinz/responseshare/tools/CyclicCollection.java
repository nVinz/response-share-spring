package my.nvinz.responseshare.tools;

import java.util.Iterator;

public class CyclicCollection<T> implements Iterable<T>{

    private Iterable<T> collection;
    private Iterator<T> iterator;

    public CyclicCollection(Iterable<T> collection) {
        this.collection = collection;
        this.iterator = collection.iterator();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                if (!iterator.hasNext()) {
                    iterator = collection.iterator();
                }
                return iterator.next();
            }

        };
    }
}
