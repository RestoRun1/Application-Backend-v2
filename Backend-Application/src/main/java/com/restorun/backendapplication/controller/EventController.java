package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restorun.backendapplication.model.Event;
import com.restorun.backendapplication.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventController(EventService eventService, ObjectMapper objectMapper) {
        this.eventService = eventService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/saveEvent")
    @Operation(summary = "Save an event", description = "Saves a new event or updates an existing event based on the provided details")
    public ResponseEntity<String> saveEvent(@RequestBody JsonNode eventJson) {
        Event event;
        try {
            event = objectMapper.treeToValue(eventJson, Event.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error processing JSON\"}");
        }

        boolean isSaved = eventService.saveEvent(event);
        if (isSaved) {
            return ResponseEntity.ok("{\"message\": \"Event saved successfully\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to save event\"}");
        }
    }

    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        boolean deleted = eventService.deleteEvent(id);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Event deleted successfully");
    }

    @GetMapping("/retrieveAllEvents")
    public ResponseEntity<List<Event>> retrieveAllEvents() {
        List<Event> events = eventService.retrieveAllEvents();
        if (events.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping("/retrieveEventById/{id}")
    public ResponseEntity<Event> retrieveEventById(@PathVariable Long id) {
        Event event = eventService.retrieveEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
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
