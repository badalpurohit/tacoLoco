package com.tacoloco.order.Controller;

import com.tacoloco.order.Domain.ItemName;
import com.tacoloco.order.Domain.OrderDetails;
import com.tacoloco.order.Domain.OrderTotalRequest;
import com.tacoloco.order.Domain.OrderTotalResponse;
import com.tacoloco.order.TestDataSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class OrderTotalIntegrationTest {

    private OrderTotalRequest testOrderTotalRequest;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup() {
        testOrderTotalRequest = TestDataSetup.getOrderTotalRequest();
    }

    @Test
    public void shouldReturnHttpStatusOKWhenValidOrderDetailsAreProvided() {

        ResponseEntity<OrderTotalResponse> response = testRestTemplate.postForEntity("/v1/orderTotal", testOrderTotalRequest, OrderTotalResponse.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldReturnOrderTotalResponseWhenValidOrderDetailsAreProvided() {

        ResponseEntity<OrderTotalResponse> response = testRestTemplate.postForEntity("/v1/orderTotal", testOrderTotalRequest, OrderTotalResponse.class);

        assertThat(response.getBody(), equalTo(TestDataSetup.getOrderTotalResponse()));
    }

    @Test
    public void shouldSendResponseAsBadRequestWhenOrderNumberIsNull() {
        testOrderTotalRequest.setOrderNumber(null);
        ResponseEntity<OrderTotalResponse> response = testRestTemplate.postForEntity("/v1/orderTotal", testOrderTotalRequest, OrderTotalResponse.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldSendResponseAsBadRequestWhenOrderNumberIsLessThanOne() {
        testOrderTotalRequest.setOrderNumber(0);
        ResponseEntity<OrderTotalResponse> response = testRestTemplate.postForEntity("/v1/orderTotal", testOrderTotalRequest, OrderTotalResponse.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldSendResponseAsBadRequestWhenOrderListIsNull() {
        testOrderTotalRequest.setOrderDetailsList(null);
        ResponseEntity<OrderTotalResponse> response = testRestTemplate.postForEntity("/v1/orderTotal", testOrderTotalRequest, OrderTotalResponse.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldSendResponseAsBadRequestWhenOrderListIsEmpty() {

        List<OrderDetails> emptyOrderList = new ArrayList<>();
        testOrderTotalRequest.setOrderDetailsList(emptyOrderList);

        ResponseEntity<OrderTotalResponse> response = testRestTemplate.postForEntity("/v1/orderTotal", testOrderTotalRequest, OrderTotalResponse.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldSendResponseAsBadRequestWhenOrderQuanityisLessThan1() {
        List<OrderDetails> emptyOrderList = new ArrayList<>();
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setItemName(ItemName.VeggieTaco);
        orderDetails.setItemQuantity(0);
        testOrderTotalRequest.setOrderDetailsList(emptyOrderList);

        ResponseEntity<OrderTotalResponse> response = testRestTemplate.postForEntity("/v1/orderTotal", testOrderTotalRequest, OrderTotalResponse.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

    }


}
