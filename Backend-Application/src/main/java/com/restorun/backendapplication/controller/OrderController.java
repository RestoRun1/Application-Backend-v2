package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
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
    private final ObjectMapper objectMapper;

    // Autowire might be unnecessary.
    @Autowired
    public OrderController(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/saveOrder")
    public ResponseEntity<String> saveOrder(@RequestBody JsonNode orderJson) {
        try {
            List<Long> mealIds = objectMapper.readValue(
                    orderJson.get("mealIds").toString(), new TypeReference<List<Long>>(){}
            );
            Long tableId = orderJson.get("tableId").asLong();
            String status = orderJson.get("status").asText();

            boolean isSaved = orderService.saveOrder(mealIds, tableId, status);
            if (isSaved) {
                return ResponseEntity.ok("{\"message\": \"Order saved successfully\"}");
            } else {
                return ResponseEntity.badRequest().body("{\"error\": \"Failed to save order\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error processing JSON: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/retrieveOrderById/{id}")
    public ResponseEntity<Order> retrieveOrderById(@PathVariable Long id) {
        Order order = orderService.retrieveOrderById(id).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
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
