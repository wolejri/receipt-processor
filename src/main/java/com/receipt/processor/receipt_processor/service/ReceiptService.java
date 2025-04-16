package com.receipt.processor.receipt_processor.service;

import com.receipt.processor.receipt_processor.model.Receipt;

public interface ReceiptService {
    int processReceipt(Receipt receipt);
}
