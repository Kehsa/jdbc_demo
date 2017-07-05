package com.kehsa.icontainer;

import java.util.Iterator;

/**
 * container interface
 * Created on 03.10.15.
 * @author kehsa
 * @param <GType> contained type
 */
public interface IContainer<GType> {
    /**
     * getter for i element of collection.
     * @param i position of element
     * @return GType element
     * @throws IndexOutOfBoundsException
     */
    GType get(final int i);
    /**
     * setter for i element of collection.
     * @param i position of element
     * @param obj new element
     */
    void set(final int i, final GType obj);
    /**
     * return count of elements of collection.
     * @return int size
     */
    int size();
    /**
     * return true if collection have not elements.
     * @return boolean empty?
     */
    boolean isEmpty();
    /**
     * get iterator.
     * @return Iterator<PhoneDB>
     */
    Iterator<GType> iterator();
    /**
     * add object to end of collection.
     * @param obj new object
     */
    void add(final GType obj);
    /**
     * remove an object from collection.
     * @param obj removed object
     */
    void delete(final GType obj);
    /**
     * remove all elements of collection.
     */
    void clear();
}
