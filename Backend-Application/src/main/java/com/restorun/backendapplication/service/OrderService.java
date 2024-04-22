package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Order;
import com.restorun.backendapplication.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Order> retrieveOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public boolean saveOrder(Order order) {
        orderRepository.save(order);
        return true;
    }

    public boolean deleteOrder(Optional<Order> order) {
        if (order.isPresent()){
            orderRepository.delete(order.get());
            return true;
        }
        return true;
    }
    public boolean updateOrder(Order order) {
        return orderRepository.findById(order.getId())
                .map(existingOrder -> {
                    existingOrder.setDiningTable(existingOrder.getDiningTable());
                    existingOrder.setStatus(existingOrder.getStatus());
                    existingOrder.setMeals(existingOrder.getMeals());
                    existingOrder.setTotalPrice(existingOrder.getTotalPrice());
                    orderRepository.save(existingOrder);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + order.getId()));
    }

    public List<Order> retrieveAllOrders(){return orderRepository.findAll();}
}
