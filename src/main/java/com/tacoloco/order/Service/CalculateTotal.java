package com.tacoloco.order.Service;

import com.tacoloco.order.Domain.ItemName;
import com.tacoloco.order.Domain.OrderDetails;
import com.tacoloco.order.Domain.OrderTotalRequest;
import com.tacoloco.order.Domain.OrderTotalResponse;
import com.tacoloco.order.Entity.PriceChart;
import com.tacoloco.order.Repository.PriceChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class CalculateTotal {

    private static DecimalFormat df = new DecimalFormat("0.00");

    @Autowired
    private PriceChartRepository priceChartRepository;

    @Value("${minNumberOfTacosForDiscount}")
    private int minNumberOfTacosForDiscount;

    @Value("${discountPercentage}")
    private Double discountPercentage;

    @Value("${taxPercentage}")
    private Double taxPercentage;

    public OrderTotalResponse calculateOrderTotal(OrderTotalRequest orderTotalRequest) {
        OrderTotalResponse orderTotalResponse = new OrderTotalResponse();

        Double subTotal = 0.0;
        Double taxAmount = 0.0;
        Double discountAmount = 0.0;
        Double orderTotal = 0.0;

        List<OrderDetails> orderDetailsList = orderTotalRequest.getOrderDetailsList();
        for (int order = 0; order < orderDetailsList.size(); order++) {
            Double subTotalForOrder = findPricePerItem(orderDetailsList.get(order).getItemName());
            subTotal += (subTotalForOrder * orderDetailsList.get(order).getItemQuantity());
        }

        BigDecimal taxAmountBigDecimal = new BigDecimal(subTotal * taxPercentage).setScale(2, RoundingMode.HALF_UP);
        taxAmount = taxAmountBigDecimal.doubleValue();

        discountAmount = calculateDiscount(orderDetailsList, subTotal);
        orderTotal = subTotal+taxAmount-discountAmount;

        orderTotalResponse.setOrderTotalRequest(orderTotalRequest);
        orderTotalResponse.setSubTotal(subTotal);
        orderTotalResponse.setTaxAmount(taxAmount);
        orderTotalResponse.setDiscountAmount(discountAmount);
        orderTotalResponse.setOrderTotal(orderTotal);

        return orderTotalResponse;

    }


    public Double findPricePerItem(ItemName itemName) {

        Optional<PriceChart> priceChart = priceChartRepository.findById(itemName.toString());

        return priceChart.get().getItemPrice();
    }


    private Double calculateDiscount(List<OrderDetails> orderDetailsList, Double subTotal) {

        Integer totalNumberOfTacos = 0;
        Double discountAmount;

        if (orderDetailsList.size() >= minNumberOfTacosForDiscount) {
            BigDecimal discountAmountBigDecimal = new BigDecimal(subTotal * discountPercentage).setScale(2, RoundingMode.HALF_UP);
            discountAmount = discountAmountBigDecimal.doubleValue();
        } else {

            for (int order = 0; order < orderDetailsList.size(); order++) {
                totalNumberOfTacos += orderDetailsList.get(order).getItemQuantity();
                if (totalNumberOfTacos > 3) {
                    break;
                }
            }
            if (totalNumberOfTacos > 3) {
                BigDecimal discountAmountBigDecimal = new BigDecimal(subTotal * discountPercentage).setScale(2, RoundingMode.HALF_UP);
                discountAmount = discountAmountBigDecimal.doubleValue();
            } else {
                discountAmount = 0.0;
            }

        }
        return discountAmount;
    }
}
