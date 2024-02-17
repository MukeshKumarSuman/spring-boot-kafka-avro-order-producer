package com.nps.controller;

import com.nps.dto.OrderDTO;
import com.nps.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> saveUser(@RequestBody OrderDTO orderDTO) {
        logger.info("Received Order in Controller => {}", orderDTO);
        OrderDTO newOrder = orderService.newOrder(orderDTO);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }
}
