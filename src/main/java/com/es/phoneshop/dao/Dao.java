package com.es.phoneshop.dao;

import com.es.phoneshop.exception.NoSuchItemException;
import com.es.phoneshop.model.Identifiable;

import java.util.ArrayList;
import java.util.List;

public abstract class Dao<T1, T2 extends Identifiable<T1>> {
    final List<T2> items = new ArrayList<>();

    public T2 get(T1 id){
        synchronized (items){
            return items.stream()
                    .filter(order -> id.equals(order.getId()))
                    .findAny()
                    .orElseThrow(()-> new NoSuchItemException(id.toString()));
        }
    }

    public void save(T2 item){
        T1 id = item.getId();
        if(id == null) throw new IllegalArgumentException("Trying to save item with null id");
        synchronized (items){
            if(itemExists(id)){
                throw new RuntimeException("Item with id " + id + " already exists.");
            } else {
                items.add(item);
            }
        }
    }

    private boolean itemExists(T1 id){
        return items.stream().anyMatch(order -> id.equals(order.getId()));
    }
}

