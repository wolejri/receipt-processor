package com.receipt.processor.receipt_processor.error;

public class ReceiptNotFoundException extends RuntimeException{
    public ReceiptNotFoundException(){
        super("No receipt found for that ID.");
    }
}
