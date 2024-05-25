package com.att.tdp.bisbis10.auxillary;

import com.att.tdp.bisbis10.entities.OrderItem;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class OrderItemConverter implements AttributeConverter<List<OrderItem>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<OrderItem> orderItemList) {
        List<String> stringList = orderItemList.stream().map(OrderItem::toString).toList();
        return !stringList.isEmpty() ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public List<OrderItem> convertToEntityAttribute(String string) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (String orderStr : string.split(SPLIT_CHAR)){
            List<String> tokens = Arrays.stream(orderStr.split(":")).toList();
            try {
                Long dishId = Long.parseLong(tokens.get(0));
                Integer amount = Integer.parseInt(tokens.get(1));
                orderItems.add(new OrderItem(dishId, amount));
            } catch (Exception ignored){}
        }
        return orderItems;
    }
}