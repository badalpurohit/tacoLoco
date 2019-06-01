package com.tacoloco.order.Controller;

import com.tacoloco.order.Domain.OrderTotalRequest;
import com.tacoloco.order.Domain.OrderTotalResponse;
import com.tacoloco.order.Service.CalculateTotal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class OrderTotal {

    @Autowired
    private CalculateTotal calculateTotalService;

    @PostMapping(value="/v1/orderTotal")
    public ResponseEntity<OrderTotalResponse> getOrderTotal(@RequestBody @Valid OrderTotalRequest orderTotalRequest){

        log.info("Request To Calculate OrderTotal Received: " + orderTotalRequest);
        OrderTotalResponse orderTotalResponse = calculateTotalService.calculateOrderTotal(orderTotalRequest);
        return ResponseEntity.ok(orderTotalResponse);
    }

}
