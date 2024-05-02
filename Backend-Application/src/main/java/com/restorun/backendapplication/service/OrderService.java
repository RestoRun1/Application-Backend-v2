package com.restorun.backendapplication.service;

import com.restorun.backendapplication.enums.PaymentStatus;
import com.restorun.backendapplication.model.DiningTable;
import com.restorun.backendapplication.model.Meal;
import com.restorun.backendapplication.model.Order;
import com.restorun.backendapplication.repository.DiningTableRepository;
import com.restorun.backendapplication.repository.MealRepository;
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
    private final MealRepository mealRepository;
    private final DiningTableRepository tableRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, MealRepository mealRepository, DiningTableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.mealRepository = mealRepository;
        this.tableRepository = tableRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Order> retrieveOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public boolean saveOrder(List<Long> mealIds, Long tableId, String status) {
        List<Meal> meals = mealRepository.findAllById(mealIds);
        DiningTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table not found"));

        double totalPrice = meals.stream().mapToDouble(Meal::getPrice).sum();
        Order order = new Order();
        order.setMeals(meals);
        order.setTable(table);
        order.setTotalPrice(totalPrice);
        order.setStatus(PaymentStatus.valueOf(status));

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
                    // existingOrder.setDiningTable(existingOrder.getDiningTable());
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
