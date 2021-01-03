package com.geekbrains.shop.service;

import com.geekbrains.shop.entities.Orders;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public void save(Order order) {
        File orderFolder = new File("D:\\temp\\orders");
        File orderFile = new File(orderFolder, order.getOrderId() + ".json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(orderFile, order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
