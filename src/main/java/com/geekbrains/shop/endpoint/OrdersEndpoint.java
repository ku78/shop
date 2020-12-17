package com.geekbrains.shop.endpoint;

import com.geekbrains.shop.entities.GetOrderRequest;
import com.geekbrains.shop.entities.GetOrderResponse;
import com.geekbrains.shop.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class OrdersEndpoint {

    private static final String NAMESPACE_URL = "http://ku78.ti/geekbrains/api/orders";

    private OrdersService ordersService;

    @Autowired
    public OrdersEndpoint(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getOrderRequest")
    @ResponsePayload
    public GetOrdersResponse getOrder(@RequestPayload GetOrdersRequest request)
            throws DatatypeConfigurationException {
        GetOrdersResponse response = new GetOrdersResponse();
        return response;
    }

}
