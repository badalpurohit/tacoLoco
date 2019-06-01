package com.tacoloco.order.Service;

import com.tacoloco.order.Domain.ItemName;
import com.tacoloco.order.Domain.OrderDetails;
import com.tacoloco.order.Domain.OrderTotalRequest;
import com.tacoloco.order.Domain.OrderTotalResponse;
import com.tacoloco.order.Entity.PriceChart;
import com.tacoloco.order.Repository.PriceChartRepository;
import com.tacoloco.order.TestDataSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculateTotalTest {

    @InjectMocks
    private CalculateTotal calculateTotalTestService;

    @Mock
    private PriceChartRepository priceChartRepository;

    private OrderTotalRequest orderTotalRequest;
    private PriceChart veggieTacoPriceChart;
    private PriceChart chickenTacoPriceChart;
    private PriceChart beefTacoPriceChart;
    private PriceChart chorizoTacoPriceChart;
    private OrderTotalResponse actualOrderTotalResponse;

    @Before
    public void setup() {

        ReflectionTestUtils.setField(calculateTotalTestService, "minNumberOfTacosForDiscount", 4);
        ReflectionTestUtils.setField(calculateTotalTestService, "discountPercentage", 0.2);
        ReflectionTestUtils.setField(calculateTotalTestService, "taxPercentage", 0.06);
        orderTotalRequest = TestDataSetup.getOrderTotalRequest();
        veggieTacoPriceChart = getPriceChart(ItemName.VeggieTaco, 2.50);
        chickenTacoPriceChart = getPriceChart(ItemName.ChickenTaco, 3.00);
        beefTacoPriceChart = getPriceChart(ItemName.ChickenTaco, 3.00);
        chorizoTacoPriceChart = getPriceChart(ItemName.ChorizoTaco, 3.50);

        when(priceChartRepository.findById(ItemName.VeggieTaco.toString())).thenReturn(Optional.ofNullable(veggieTacoPriceChart));
        when(priceChartRepository.findById(ItemName.ChickenTaco.toString())).thenReturn(Optional.ofNullable(chickenTacoPriceChart));
        when(priceChartRepository.findById(ItemName.BeefTaco.toString())).thenReturn(Optional.ofNullable(beefTacoPriceChart));
        when(priceChartRepository.findById(ItemName.ChorizoTaco.toString())).thenReturn(Optional.ofNullable(chorizoTacoPriceChart));

        actualOrderTotalResponse = calculateTotalTestService.calculateOrderTotal(orderTotalRequest);
    }

    @Test
    public void shouldFindPriceForVeggieTaco() {

        Double itemPrice = calculateTotalTestService.findPricePerItem(ItemName.VeggieTaco);

        assertThat(itemPrice, equalTo(2.50));
    }

    @Test
    public void shouldFindPriceForChickenTaco() {

        Double itemProce = calculateTotalTestService.findPricePerItem(ItemName.ChickenTaco);

        assertThat(itemProce, equalTo(3.00));
    }

    @Test
    public void shouldFindPriceForBeefTaco() {

        Double itemProce = calculateTotalTestService.findPricePerItem(ItemName.BeefTaco);

        assertThat(itemProce, equalTo(3.00));

    }

    @Test
    public void shouldFindPriceForChorizoTaco() {

        Double itemProce = calculateTotalTestService.findPricePerItem(ItemName.ChorizoTaco);

        assertThat(itemProce, equalTo(3.50));

    }

    @Test
    public void shouldCalculateSubTotalWhenValidOrderRequestIsProvided() {

        assertThat(actualOrderTotalResponse.getSubTotal(), equalTo(11.00));
    }

    @Test
    public void shouldcalculateTaxAmountonSubTotal() {

        assertThat(actualOrderTotalResponse.getTaxAmount(), equalTo(0.66));
    }

    @Test
    public void shouldCalculateDiscountIfTotalNumberOfTacosInOrderMoreThan3() {

        assertThat(actualOrderTotalResponse.getDiscountAmount(), equalTo(2.2));
    }


    @Test
    public void shouldCalculateTotalAmountforOrderMoreThan3() {
        assertThat(actualOrderTotalResponse.getOrderTotal(), equalTo(9.46));
    }

    @Test
    public void shouldSetDiscountToZeroWhenNumberOfTacosInOrderis3() {
        orderTotalRequest.getOrderDetailsList().forEach(orderDetails -> orderDetails.setItemQuantity(1));
        OrderTotalResponse orderTotalResponse = calculateTotalTestService.calculateOrderTotal(orderTotalRequest);
        assertThat(orderTotalResponse.getDiscountAmount(), equalTo(0.0));
    }

    @Test
    public void shouldCalculateTotalAmountForBulkOrder() {
        OrderTotalResponse orderTotalResponse = calculateTotalTestService.calculateOrderTotal(getBulkOrderRequest());

        assertThat(orderTotalResponse.getSubTotal(), equalTo(240.0));
        assertThat(orderTotalResponse.getDiscountAmount(), equalTo(48.0));
        assertThat(orderTotalResponse.getTaxAmount(), equalTo(14.4));
        assertThat(orderTotalResponse.getOrderTotal(), equalTo(206.4));
    }

    private OrderTotalRequest getBulkOrderRequest() {

        OrderTotalRequest orderTotalRequest = new OrderTotalRequest();
        orderTotalRequest.setOrderNumber(2);
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        OrderDetails orderDetailsVegTaco = new OrderDetails();
        orderDetailsVegTaco.setItemName(ItemName.VeggieTaco);
        orderDetailsVegTaco.setItemQuantity(20);

        OrderDetails orderDetailsChickenTaco = new OrderDetails();
        orderDetailsChickenTaco.setItemName(ItemName.ChickenTaco);
        orderDetailsChickenTaco.setItemQuantity(20);

        OrderDetails orderDetailsBeefTaco = new OrderDetails();
        orderDetailsBeefTaco.setItemName(ItemName.BeefTaco);
        orderDetailsBeefTaco.setItemQuantity(20);

        OrderDetails orderDetailsChorizoTaco = new OrderDetails();
        orderDetailsChorizoTaco.setItemName(ItemName.ChorizoTaco);
        orderDetailsChorizoTaco.setItemQuantity(20);

        orderDetailsList.add(orderDetailsVegTaco);
        orderDetailsList.add(orderDetailsChickenTaco);
        orderDetailsList.add(orderDetailsBeefTaco);
        orderDetailsList.add(orderDetailsChorizoTaco);
        orderTotalRequest.setOrderDetailsList(orderDetailsList);
        return orderTotalRequest;
    }


    private PriceChart getPriceChart(ItemName itemName, Double itemPrice) {

        PriceChart priceChart = new PriceChart();
        priceChart.setItemName(itemName.toString());
        priceChart.setItemPrice(itemPrice);
        return priceChart;
    }


}
