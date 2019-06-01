package com.tacoloco.order.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTotalResponse {

    private OrderTotalRequest orderTotalRequest;
    private Double subTotal;
    private Double taxAmount;
    private Double discountAmount;
    private Double orderTotal;
}
