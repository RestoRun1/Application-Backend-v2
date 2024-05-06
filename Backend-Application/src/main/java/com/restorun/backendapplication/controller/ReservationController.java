package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.dto.ReservationDTO;
import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.model.DiningTable;
import com.restorun.backendapplication.model.Reservation;
import com.restorun.backendapplication.service.CustomerService;
import com.restorun.backendapplication.service.DiningTableService;
import com.restorun.backendapplication.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    //private final CustomerService customerService;
    //private final DiningTableService diningTableService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReservationController(ReservationService reservationService, CustomerService customerService, DiningTableService diningTableService, ObjectMapper objectMapper) {
        this.reservationService = reservationService;
/*        this.customerService = customerService;
        this.diningTableService = diningTableService;*/
        this.objectMapper = objectMapper;
    }

    @GetMapping("/retrieveReservationById/{id}")
    public ResponseEntity<Reservation> retrieveReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.retrieveReservationById(id).orElse(null);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/saveReservation")
    @Operation(summary = "Save a reservation", description = "Saves a new reservation or updates an existing reservation based on the provided details")
    public ResponseEntity<String> saveReservation(@RequestBody JsonNode reservationJson) {
        Long tableId = reservationJson.get("tableId").asLong();
        Long customerId = reservationJson.get("customerId").asLong();
        LocalDateTime reservationTime = LocalDateTime.parse(reservationJson.get("reservationTime").asText());
        int numberOfGuests = reservationJson.get("numberOfGuests").asInt();
        String specialRequests = reservationJson.get("specialRequests").asText();
        Long restaurantId = reservationJson.get("restaurantId").asLong();

        System.out.println("RESTAUURANT ID IS ->>>>>" + restaurantId);

        reservationService.saveReservation(restaurantId,tableId, customerId, reservationTime, numberOfGuests, specialRequests);
        return ResponseEntity.ok("{\"message\": \"Reservation saved successfully\"}");
    }

    /*@PostMapping("/saveReservation")
    @Operation(summary = "Save a reservation", description = "Saves a new reservation or updates an existing reservation based on the provided details")
    public ResponseEntity<String> saveReservation(@RequestBody JsonNode reservationJson) {
        try {
            ReservationDTO reservationDTO = objectMapper.treeToValue(reservationJson, ReservationDTO.class);
            Reservation reservation = convertToEntity(reservationDTO);
            reservationService.saveReservation(reservation);
            return ResponseEntity.ok("Reservation saved successfully");
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Invalid reservation details provided: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Database integrity violation: " + e.getMessage());
        }
    }


    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        // Assuming the service or a method to handle customer and table lookup or creation
        Customer customer = customerService.findOrCreateCustomer(reservationDTO.getCustomer());
        DiningTable table = diningTableService.findOrCreateTable(reservationDTO.getTable());

        reservation.setCustomer(customer);
        reservation.setTable(table);
        reservation.setReservationTime(reservationDTO.getReservationTime());
        reservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());
        reservation.setSpecialRequests(reservationDTO.getSpecialRequests());

        return reservation;
    }*/


    @DeleteMapping("/deleteReservation/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        boolean deleted = reservationService.deleteReservation(reservationService.retrieveReservationById(id));
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
