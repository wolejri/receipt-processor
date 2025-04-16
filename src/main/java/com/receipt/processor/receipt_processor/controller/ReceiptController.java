package com.receipt.processor.receipt_processor.controller;


import com.receipt.processor.receipt_processor.error.ReceiptNotFoundException;
import com.receipt.processor.receipt_processor.model.Receipt;
import com.receipt.processor.receipt_processor.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private Map<String, Integer> pointsStorage = new HashMap<>();
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService){
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipts(@RequestBody @Valid Receipt receipt){
        String receiptID = UUID.randomUUID().toString();
        int points = receiptService.processReceipt(receipt);
        pointsStorage.put(receiptID, points);
        return new ResponseEntity<>(Map.of("id", receiptID), HttpStatus.OK);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getReceiptPoints(@PathVariable String id){
        if(!pointsStorage.containsKey(id)){
            throw new ReceiptNotFoundException();
        }
        return new ResponseEntity<>(Map.of("points", pointsStorage.get(id)), HttpStatus.OK);
    }
}
