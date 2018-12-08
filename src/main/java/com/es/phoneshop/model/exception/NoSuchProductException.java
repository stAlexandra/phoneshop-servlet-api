package com.es.phoneshop.model.exception;

public class NoSuchProductException extends RuntimeException{
    public NoSuchProductException(){
        super();
    }

    public NoSuchProductException(Long productId){
        super("No product with id "+ productId);
    }
}
