package com.tacoloco.order.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private ItemName itemName;

    @NotNull(message = "Please provide minimum value of 1 for Item Quantity, it cannot be NULL")
    @Valid
    @Min(value = 1, message = "Please provide minimum value of 1 for Item Quantity")
    private Integer itemQuantity;

}
