package com.tacoloco.order.Controller;

import com.tacoloco.order.Domain.OrderTotalRequest;
import com.tacoloco.order.Domain.OrderTotalResponse;
import com.tacoloco.order.Service.CalculateTotal;
import com.tacoloco.order.TestDataSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class OrderTotalUnitTest {

    @InjectMocks
    private OrderTotal orderTotal;

    @Mock
    private CalculateTotal calculateTotalService;

    private OrderTotalRequest orderTotalRequest;
    private OrderTotalResponse orderTotalResponse;
    private ResponseEntity<OrderTotalResponse> response;

    @Before
    public void setup() {
        orderTotalRequest = TestDataSetup.getOrderTotalRequest();
        orderTotalResponse = TestDataSetup.getOrderTotalResponse();
        when(calculateTotalService.calculateOrderTotal(orderTotalRequest)).thenReturn(orderTotalResponse);

        response = orderTotal.getOrderTotal(orderTotalRequest);
    }


    @Test
    public void shouldReturnHttpStatusOkWhenValidRequest() {

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

    }

    @Test
    public void shouldReturnOrderReponseWhenValidRequest(){

        assertThat(response.getBody(),equalTo(orderTotalResponse));

    }

    @Test
    public void shouldCallCalculateServiceWhenValidRequest(){
        verify(calculateTotalService,times(1)).calculateOrderTotal(orderTotalRequest);
    }
}
