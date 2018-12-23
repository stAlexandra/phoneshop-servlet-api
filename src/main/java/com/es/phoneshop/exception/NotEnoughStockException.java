package com.es.phoneshop.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(Integer wantedQuantity) {
        super("Quantity " + wantedQuantity + " exceeds stock for the product.");
    }
}
