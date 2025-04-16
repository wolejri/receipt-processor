package com.receipt.processor.receipt_processor.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    @NotBlank(message = "Short description is required")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Short description contains invalid characters")
    private String shortDescription;

    @NotBlank(message = "Price is required")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be a decimal with two digits (e.g., 6.49)")
    private String price;
}
