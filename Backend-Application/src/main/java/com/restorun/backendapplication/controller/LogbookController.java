package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Logbook;
import com.restorun.backendapplication.service.LogbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/logbook")
public class LogbookController {
    private final LogbookService logbookService;

    @Autowired
    public LogbookController(LogbookService logbookService) {
        this.logbookService = logbookService;
    }

    @GetMapping("/retrieveLogbookById")
    public ResponseEntity<Logbook> retrieveLogbookById(@RequestBody Long id) {
        Optional<Logbook> logbook = logbookService.retrieveLogbookById(id);
        return logbook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveLogbook")
    public ResponseEntity<String> saveLogbook(@RequestBody String logbook) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Logbook logbookObj = mapper.readValue(logbook, Logbook.class);
        boolean saved = logbookService.saveLogbook(logbookObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Logbook saved successfully");
    }

    @DeleteMapping("/deleteLogbook")
    public ResponseEntity<String> deleteLogbook(@RequestBody Long id) {
        Optional<Logbook> logbook = logbookService.retrieveLogbookById(id);
        if (logbook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = logbookService.deleteLogbook(logbook);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Logbook deleted successfully");
    }
}
