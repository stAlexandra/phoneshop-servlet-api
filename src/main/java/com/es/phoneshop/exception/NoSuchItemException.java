package com.es.phoneshop.exception;

import java.util.NoSuchElementException;

public class NoSuchItemException extends NoSuchElementException {
    public NoSuchItemException() {
        super();
    }

    public NoSuchItemException(String id) {
        super("No item with id: " + id);
    }
}
