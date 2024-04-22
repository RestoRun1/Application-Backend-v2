package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Bill;
import com.restorun.backendapplication.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    private final BillService billService;
    private final ObjectMapper objectMapper;

    // Autowire might be unnecessary.
    @Autowired
    public BillController(BillService billService, ObjectMapper objectMapper) {
        this.billService = billService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/retrieveBillById")
    @Operation(summary = "Retrieve a bill by ID", description = "Provide an billId JSON to look up a specific bill")
    public ResponseEntity<Bill> retrieveBillById(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "JSON object containing the ID of the bill to delete") JsonNode requestBody) {
        if (!requestBody.has("billId") || !requestBody.get("billId").canConvertToLong()) {
            return ResponseEntity.badRequest().build(); // Or provide a more specific error message
        }
        Long id = requestBody.get("billId").asLong();

        Optional<Bill> bill = billService.retrieveBillById(id);
        return bill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/saveBill")
    @Operation(summary = "Save a bill", description = "Saves a new bill or updates an existing bill based on the provided details")
    public ResponseEntity<String> saveBill(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Bill JSON object to be saved", required = true,
            content = @Content(schema = @Schema(implementation = Bill.class))) JsonNode billJson) throws JsonProcessingException {
        // Convert JsonNode to Bill object
        Bill bill;
        try {
            bill = objectMapper.treeToValue(billJson, Bill.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error processing JSON\"}");
        }

        // Attempt to save the bill using your service layer
        boolean isSaved = billService.saveBill(bill);
        if (isSaved) {
            // Return a success response
            return ResponseEntity.ok("{\"message\": \"Bill saved successfully\"}");
        } else {
            // Return an error response if saving the bill failed
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to save bill\"}");
        }
    }

    @DeleteMapping("/deleteBill")
    @Operation(summary = "Delete a bill", description = "Deletes a bill with the specified ID provided within the JSON body.")
    public ResponseEntity<String> deleteBill(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "JSON object containing the ID of the bill to delete", required = true,
            content = @Content(schema = @Schema(example = "{\"id\": 123}"))) JsonNode billObjectId) {
        // Extract the bill ID from the JSON object
        if (!billObjectId.has("billId") || !billObjectId.get("billId").canConvertToLong()) {
            return ResponseEntity.badRequest().body("{\"error\": \"Invalid or missing 'id' field in JSON\"}");
        }
        Optional<Bill> bill = billService.retrieveBillById(billObjectId.get("billId").asLong());

        // Attempt to delete the bill using your service layer
        boolean deleted = billService.deleteBill(bill);
        if (deleted) {
            // Return a success response if the bill was successfully deleted
            return ResponseEntity.ok("{\"message\": \"Bill deleted successfully\"}");
        } else {
            // Return an error response if the deletion failed (e.g., bill not found)
            return ResponseEntity.notFound().build();
        }
    }


    // return all bills in the system
    @GetMapping("/retrieveAllBills")
    @Operation(summary = "Retrieve all bills", description = "Retrieves a list of all bills in the system")
    public ResponseEntity<List<Bill>> retrieveAllBills() {

        List<Bill> bills = billService.retrieveAllBills();
        if (bills.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bills);

    }
}
