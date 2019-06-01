package com.tacoloco.order;

import com.tacoloco.order.Domain.ItemName;
import com.tacoloco.order.Domain.OrderDetails;
import com.tacoloco.order.Domain.OrderTotalRequest;
import com.tacoloco.order.Domain.OrderTotalResponse;

import java.util.ArrayList;
import java.util.List;

public class TestDataSetup {

    public static OrderTotalRequest getOrderTotalRequest() {
        OrderTotalRequest orderTotalRequest = new OrderTotalRequest();
        orderTotalRequest.setOrderNumber(1);
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        OrderDetails orderDetailsVegTaco = new OrderDetails();
        orderDetailsVegTaco.setItemName(ItemName.VeggieTaco);
        orderDetailsVegTaco.setItemQuantity(2);

        OrderDetails orderDetailsChickenTaco = new OrderDetails();
        orderDetailsChickenTaco.setItemName(ItemName.ChickenTaco);
        orderDetailsChickenTaco.setItemQuantity(1);

        OrderDetails orderDetailsBeefTaco = new OrderDetails();
        orderDetailsBeefTaco.setItemName(ItemName.BeefTaco);
        orderDetailsBeefTaco.setItemQuantity(1);

        orderDetailsList.add(orderDetailsVegTaco);
        orderDetailsList.add(orderDetailsChickenTaco);
        orderDetailsList.add(orderDetailsBeefTaco);
        orderTotalRequest.setOrderDetailsList(orderDetailsList);
        return orderTotalRequest;
    }

    public static OrderTotalResponse getOrderTotalResponse() {

        OrderTotalResponse orderTotalResponse = new OrderTotalResponse();
        orderTotalResponse.setOrderTotal(9.46);
        orderTotalResponse.setDiscountAmount(2.2);
        orderTotalResponse.setTaxAmount(0.66);
        orderTotalResponse.setSubTotal(11.00);
        orderTotalResponse.setOrderTotalRequest(getOrderTotalRequest());

        return orderTotalResponse;
    }

}
