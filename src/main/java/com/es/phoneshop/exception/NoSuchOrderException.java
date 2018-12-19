package com.es.phoneshop.exception;

import java.util.NoSuchElementException;

public class NoSuchOrderException extends NoSuchElementException {
    public NoSuchOrderException() {
        super();
    }

    public NoSuchOrderException(String id) {
        super("No order with id: " + id);
    }
}
