package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Order;
import com.restorun.backendapplication.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    // Autowire might be unnecessary.
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/retrieveOrderById")
    public ResponseEntity<Order> retrieveOrderById(@RequestBody Long id) {
        Optional<Order> order = orderService.retrieveOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveOrder")
    public ResponseEntity<String> saveOrder(@RequestBody String order) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Order orderObj = mapper.readValue(order, Order.class);
        boolean saved = orderService.saveOrder(orderObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Order saved successfully");
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<String> deleteOrder(@RequestBody Long id) {
        Optional<Order> order = orderService.retrieveOrderById(id);
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = orderService.deleteOrder(order);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Order deleted successfully");

    }


    // return all orders in the system
    @GetMapping("/retrieveAllOrders")
    public ResponseEntity<List<Order>> retrieveAllOrders() {
        List<Order> orders = orderService.retrieveAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);
    }
}
