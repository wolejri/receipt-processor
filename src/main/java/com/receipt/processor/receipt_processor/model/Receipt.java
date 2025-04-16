package com.receipt.processor.receipt_processor.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {

    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Retailer name contains invalid characters")
    @NotEmpty(message = "Retailer name cannot be empty.")
    private String retailer;

    @NotNull(message = "Purchase date is required")
    private LocalDate purchaseDate;

    @NotNull(message = "Purchase time is required")
    private LocalTime purchaseTime;

    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be a decimal with two digits after the point (e.g., 6.49)")
    private String total;

    @NotEmpty(message = "At least one item is required")
    private List<Item> items;




}
