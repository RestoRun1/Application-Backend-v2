package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Event;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.repository.EventRepository;
import com.restorun.backendapplication.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public EventService(EventRepository eventRepository, RestaurantRepository restaurantRepository) {
        this.eventRepository = eventRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Event retrieveEventById(Long id) {
        return eventRepository.findById(id)
                .orElse(null);
    }
    @Transactional
    public boolean deleteEvent(Long id) {
        boolean exists = eventRepository.existsById(id);

        if (!exists) {
            throw new RuntimeException("Event with id " + " does not exist");
        }
        eventRepository.deleteById(id);
        return true;
    }

    //updateEvent in model??
    @Transactional
    public boolean updateEvent(Event event) {
        return eventRepository.findById(event.getId())
                .map(e -> {
                    e.updateEvent(event);
                    eventRepository.save(e);
                    return true; // Successfully updated
                })
                .orElseThrow(() -> new RuntimeException("Event not found with id " + event.getId()));
    }

    @Transactional
    public boolean saveEvent(Long restaurantId, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Event event = new Event();
        event.setRestaurant(restaurant);
        event.setTitle(title);
        event.setDescription(description);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        eventRepository.save(event);
        return true;
    }

    @Transactional
    public List<Event> retrieveAllEvents(){
        return eventRepository.findAll();
    }

}
