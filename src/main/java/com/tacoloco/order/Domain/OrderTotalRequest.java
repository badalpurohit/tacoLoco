package com.tacoloco.order.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTotalRequest {
    @NotNull(message = "Please provide minimum value of 1 for OrderNumber, it cannot be NULL")
    @Valid
    @Min(value = 1,message = "Please provide minimum value of 1 for OrderNumber")
    private Integer orderNumber;

    @NotNull(message = "Please provide atleast one entry for orderDetailsList, it cannot be NULL")
    @Valid
    @Size(min=1,message = "Please provide atleast one entry for orderDetailsList")
    private List<OrderDetails> orderDetailsList;
}
