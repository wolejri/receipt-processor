package com.receipt.processor.receipt_processor.service;


import com.receipt.processor.receipt_processor.model.Item;
import com.receipt.processor.receipt_processor.model.Receipt;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Map;

@Service
public class ReceiptServiceImpl implements ReceiptService{

    private final double TWO_FIVE_DIVISOR = 0.25;
    private final int EVERY_TWO_ITEMS_POINTS = 5;
    private final int TRIMMED_LENGTH_DIVIDER = 3;
    private final double PRICE_MULTIPLIER = 0.2;
    private final int ODD_DAY_POINTS = 6;
    private final int ROUND_TOTAL_POINTS = 50;
    private final int POINT_TWO_FIVE_POINTS = 25;
    private final int TIME_WINDOW_POINTS = 10;


    @Override
    public int processReceipt(Receipt receipt) {
        return alphanumbericCharacterPoints(receipt)
                + roundTotalPoints(receipt)
                + multipleOfPointTwoFivePoints(receipt)
                + itemsPairCountPoints(receipt)
                + descriptionLengthPoints(receipt)
                + oddDayPoints(receipt)
                + timeOfPurchasePoints(receipt);
    }

    private int alphanumbericCharacterPoints(Receipt receipt){
        return (int) receipt.getRetailer().chars().filter(Character::isLetterOrDigit).count();
    }

    private int roundTotalPoints(Receipt receipt){
        return receipt.getTotal().endsWith(".00") ? ROUND_TOTAL_POINTS : 0;
    }

    private int multipleOfPointTwoFivePoints(Receipt receipt){
        Double total = Double.parseDouble(receipt.getTotal());
        return total % TWO_FIVE_DIVISOR == 0.0 ? POINT_TWO_FIVE_POINTS : 0;
    }

    private int itemsPairCountPoints(Receipt receipt){
        return (receipt.getItems().size() / 2 ) * EVERY_TWO_ITEMS_POINTS;
    }

    private int descriptionLengthPoints(Receipt receipt){
        int totalPoints = 0;
        for(Item item : receipt.getItems()){
            String descriptionForItem = item.getShortDescription().trim();
            if(descriptionForItem.length() % TRIMMED_LENGTH_DIVIDER == 0){
                double itemPrice = Double.parseDouble(item.getPrice());
                int points = (int) Math.ceil(itemPrice * PRICE_MULTIPLIER);
                totalPoints += points;
            }
        }
        return totalPoints;
    }

    private int oddDayPoints(Receipt receipt){
        return receipt.getPurchaseDate().getDayOfMonth() % 2 != 0 ? ODD_DAY_POINTS : 0;
    }

    private int timeOfPurchasePoints(Receipt receipt){
        LocalTime timeOfPurchase = receipt.getPurchaseTime();
        return timeOfPurchase.isAfter(LocalTime.of(14,0)) && timeOfPurchase.isBefore(LocalTime.of(16,0)) ? TIME_WINDOW_POINTS : 0;
    }


}
