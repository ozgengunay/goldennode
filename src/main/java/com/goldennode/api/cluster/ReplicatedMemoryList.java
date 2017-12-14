package com.goldennode.api.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.LoggerFactory;

public class ReplicatedMemoryList<E> extends ReplicatedMemoryObject implements List<E> {
    private static final long serialVersionUID = 1L;
    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ReplicatedMemoryList.class);
    protected List<E> innerList = Collections.synchronizedList(new ArrayList<E>());

    public ReplicatedMemoryList() {
        super();
    }

    public ReplicatedMemoryList(String publicName) {
        super(publicName);
    }

    @Override
    public boolean add(E e) {
        if (getCluster() == null) {
            return _add(e);
        }
        return (boolean) safeOperate(new Operation(getPublicName(), "add", e));
    }

    public boolean _add(E e) {
        Boolean b = _base_add(e);
        createUndoRecord(new Operation(getPublicName(), "base_remove", e));
        return b;
    }

    public boolean _base_add(E e) {
        return innerList.add(e);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        if (getCluster() == null) {
            return _set(index, element);
        }
        return (E) safeOperate(new Operation(getPublicName(), "set", index, element));
    }

    public E _set(int index, E element) {
        E e = _base_set(index, element);
        createUndoRecord(new Operation(getPublicName(), "base_set", index, e));
        return e;
    }

    public E _base_set(int index, E element) {
        E e = innerList.set(index, element);
        return e;
    }

    @Override
    public void add(int index, E element) {
        if (getCluster() == null) {
            _add(index, element);
            return;
        }
        safeOperate(new Operation(getPublicName(), "add", index, element));
    }

    public void _add(int index, E element) {
        _base_add(index, element);
        createUndoRecord(new Operation(getPublicName(), "base_remove", index));
    }

    public void _base_add(int index, E element) {
        innerList.add(index, element);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        if (getCluster() == null) {
            return _remove(index);
        }
        return (E) safeOperate(new Operation(getPublicName(), "remove", index));
    }

    public E _remove(int index) {
        E e = _base_remove(index);
        createUndoRecord(new Operation(getPublicName(), "base_add", index, e));
        return e;
    }

    public E _base_remove(int index) {
        E e = innerList.remove(index);
        return e;
    }

    @Override
    public void clear() {
        if (getCluster() == null) {
            _clear();
            return;
        }
        safeOperate(new Operation(getPublicName(), "clear"));
    }

    public void _clear() {
        ArrayList<E> al = new ArrayList<E>(innerList);
        _base_clear();
        createUndoRecord(new Operation(getPublicName(), "base_addAll", al));
    }

    public void _base_clear() {
        innerList.clear();
    }

    @Override
    public boolean remove(Object o) {
        if (getCluster() == null) {
            return _remove(o);
        }
        return (boolean) safeOperate(new Operation(getPublicName(), "remove", o));
    }

    public boolean _remove(Object o) {
        Boolean b = _base_remove(o);
        createUndoRecord(new Operation(getPublicName(), "base_add", o));
        return b;
    }

    public boolean _base_remove(Object o) {
        return innerList.remove(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public boolean _base_addAll(Collection<? extends E> c) {
        return innerList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    // Methods below don't modify list.
    @Override
    public int size() {
        return innerList.size();
    }

    @Override
    public boolean isEmpty() {
        return innerList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return innerList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return innerList.iterator();
    }

    @Override
    public Object[] toArray() {
        return innerList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return innerList.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return innerList.containsAll(c);
    }

    @Override
    public E get(int index) {
        return innerList.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return innerList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return innerList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return innerList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return innerList.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return innerList.subList(fromIndex, toIndex);
    }
}
