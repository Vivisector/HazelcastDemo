package com.zooplus.demo.service;

/**
 * Created by @author Igor Ivaniuk on 12.10.2015.
 */

import java.util.Collection;

public interface Service<T> {

    public void add(T t);

    public T get(T t);

    public T get(String s);

    public Collection<T> getAll();

    public void remove(T t);

    public void remove(String s);

    public void removeAll();
}
