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

    private static final String NAMESPACE_URL = "http://com/geekbrains/shop/api/orders";

    private OrdersService ordersService;

    @Autowired
    public OrdersEndpoint(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getOrderRequest")
    @ResponsePayload
    public GetOrderResponse getOrder(@RequestPayload GetOrderRequest request) throws DatatypeConfigurationException {
        OrderDto orderDto = ordersService.getOrderDtoById(request.getId());
        GetOrderResponse response = new GetOrderResponse();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAmount(orderDto.getAmount());
        orderInfo.setCreated(getXmlGregorianCalendarFromLocalDate(orderDto.getCreated()));
        orderInfo.setStatus(orderDto.getStatus().toString());
        orderInfo.setUsername(orderDto.getUsername());
        orderInfo.setId(orderDto.getId());
        orderInfo.setSum(orderDto.getSum());
        response.setOrder(orderInfo);
        return response;
    }

    private XMLGregorianCalendar getXmlGregorianCalendarFromLocalDate(LocalDateTime localDateTime) throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        xmlCal.setYear(localDateTime.getYear());
        xmlCal.setMonth(localDateTime.getMonthValue());
        xmlCal.setDay(localDateTime.getDayOfMonth());
        xmlCal.setHour(localDateTime.getHour());
        xmlCal.setMinute(localDateTime.getMinute());
        xmlCal.setSecond(localDateTime.getSecond());
        return xmlCal;
    }


}
