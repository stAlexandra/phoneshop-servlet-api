package com.es.phoneshop.exception;

public class NotEnoughOrderDetailsException extends RuntimeException {
    public NotEnoughOrderDetailsException(){
        super();
    }
    public NotEnoughOrderDetailsException(String missingInfo){
        super("Could not find "+ missingInfo);
    }
}
