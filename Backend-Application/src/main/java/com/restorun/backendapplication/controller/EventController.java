package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restorun.backendapplication.model.Event;
import com.restorun.backendapplication.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/allEvents")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();

        if (events.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    @DeleteMapping("/deleteEvent")
    public ResponseEntity<String> deleteEvent(@RequestBody String id) {
        boolean deleted = eventService.deleteEvent(Long.valueOf(id));

        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Event deleted successfully");
    }

    @PostMapping("/addEvent")
    public ResponseEntity<String> addEvent(@RequestBody String event) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Event eventObj = mapper.readValue(event, Event.class);

        boolean saved = eventService.addEvent(eventObj);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Event added successfully");
    }

    @PutMapping("/updateEvent")
    public ResponseEntity<String> updateEvent(@RequestBody String event) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Event eventObj = mapper.readValue(event, Event.class);

        boolean updated = eventService.updateEvent(eventObj);
        if (!updated) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Event updated successfully");
    }
}
