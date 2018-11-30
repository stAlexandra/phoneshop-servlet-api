package com.es.phoneshop.model.product.exception;

public class NoSuchProductException extends RuntimeException{
    public NoSuchProductException(){}
    public NoSuchProductException(String message){
        super(message);
    }
}
