package com.nps.service;

import avro.schema.generated.Address;
import avro.schema.generated.Order;
import avro.schema.generated.OrderLineItem;
import avro.schema.generated.Store;
import com.nps.dto.AddressDTO;
import com.nps.dto.OrderDTO;
import com.nps.dto.StoreDTO;
import com.nps.producer.OrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private OrderProducer orderProducer;

    public OrderService(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    public OrderDTO newOrder(OrderDTO orderDTO) {
        Order order = mapToOrder(orderDTO);
        orderDTO.setId(order.getId().toString());
        logger.info("Avro Order=>{}", order);
        this.orderProducer.sendMessage(order);
        return orderDTO;
    }

    private Order mapToOrder(OrderDTO orderDTO) {
        StoreDTO storeDto = orderDTO.getStore();
        AddressDTO addressDTO = storeDto.getAddress();
        Address address = Address.newBuilder()
                .setAddressLine1(addressDTO.getAddressLine1())
                .setCity(addressDTO.getCity())
                .setCountry(addressDTO.getCountry())
                .setStateProvince(addressDTO.getState())
                .setZip(addressDTO.getZip())
                .build();
        Store store = Store.newBuilder()
                .setId(storeDto.getStoreId())
                .setAddress(address)
                .build();

        List<OrderLineItem> orderLineItems = orderDTO.getOrderLineItems().stream().map(orderLineItemDTO -> OrderLineItem.newBuilder()
                .setName(orderLineItemDTO.getName())
                .setQuantity(orderLineItemDTO.getQuantity())
                .setCost(orderLineItemDTO.getCost())
                .setSize(orderLineItemDTO.getSize()).build()).collect(Collectors.toList());

        return Order.newBuilder()
                .setId(UUID.randomUUID())
                .setName(orderDTO.getName())
                .setStore(store)
                .setOrderLineItems(orderLineItems)
//                .setOrderedTime(Instant.now())
                .setOrderedTime(orderDTO.getOrderedTime().toInstant(ZoneOffset.UTC))
                .setPickUp(orderDTO.getPickUp())
                .setStatus(orderDTO.getStatus())
                .build();
    }


}
