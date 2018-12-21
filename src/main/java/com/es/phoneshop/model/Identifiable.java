package com.es.phoneshop.model;

public interface Identifiable<T> {
    T getId();
    void setId(T id);
}
