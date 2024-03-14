package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Reservation;
import com.restorun.backendapplication.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    // Autowire might be unnecessary.
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/retrieveReservationById")
    public ResponseEntity<Reservation> retrieveReservationById(@RequestBody Long id) {
        Optional<Reservation> reservation = reservationService.retrieveReservationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveReservation")
    public ResponseEntity<String> saveReservation(@RequestBody String reservation) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Reservation reservationObj = mapper.readValue(reservation, Reservation.class);
        boolean saved = reservationService.saveReservation(reservationObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Reservation saved successfully");
    }

    @DeleteMapping("/deleteReservation")
    public ResponseEntity<String> deleteReservation(@RequestBody Long id) {
        Optional<Reservation> reservation = reservationService.retrieveReservationById(id);
        if (reservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = reservationService.deleteReservation(reservation);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Reservation deleted successfully");

    }


    // return all reservations in the system
    @GetMapping("/retrieveAllReservations")
    public ResponseEntity<List<Reservation>> retrieveAllReservations() {
        List<Reservation> reservations = reservationService.retrieveAllReservations();
        if (reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }
}
