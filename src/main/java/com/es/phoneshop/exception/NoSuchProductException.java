package com.es.phoneshop.exception;

import java.util.NoSuchElementException;

public class NoSuchProductException extends NoSuchElementException {
    public NoSuchProductException(){
        super();
    }

    public NoSuchProductException(Long productId){
        super("No product with id "+ productId);
    }
}
