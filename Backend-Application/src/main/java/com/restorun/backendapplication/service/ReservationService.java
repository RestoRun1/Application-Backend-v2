package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.model.DiningTable;
import com.restorun.backendapplication.model.Reservation;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.repository.CustomerRepository;
import com.restorun.backendapplication.repository.DiningTableRepository;
import com.restorun.backendapplication.repository.ReservationRepository;
import com.restorun.backendapplication.repository.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final DiningTableRepository diningTableRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public ReservationService(RestaurantRepository restaurantRepository ,ReservationRepository reservationRepository, CustomerRepository customerRepository, DiningTableRepository diningTableRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.diningTableRepository = diningTableRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Reservation> retrieveReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public boolean saveReservation(Long restaurantId,Long tableId, Long customerId, LocalDateTime reservationTime, int numberOfGuests, String specialRequests) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        
        DiningTable table = diningTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Reservation reservation = new Reservation();
        reservation.setTable(table);
        reservation.setCustomer(customer);
        reservation.setReservationTime(reservationTime);
        reservation.setNumberOfGuests(numberOfGuests);
        reservation.setSpecialRequests(specialRequests);
        reservation.setRestaurant(restaurant);

        reservationRepository.save(reservation);
        return true;
    }


    public boolean deleteReservation(Optional<Reservation> reservation) {
        if (reservation.isPresent()){
            reservationRepository.delete(reservation.get());
            return true;
        }
        return true;
    }
    public boolean updateReservation(Reservation reservation) {
        return reservationRepository.findById(reservation.getId())
                .map(existingReservation -> {
                    existingReservation.setReservationTime(existingReservation.getReservationTime());
                    existingReservation.setCustomer(existingReservation.getCustomer());
                    existingReservation.setTable(existingReservation.getTable());
                    existingReservation.setNumberOfGuests(existingReservation.getNumberOfGuests());
                    existingReservation.setSpecialRequests(existingReservation.getSpecialRequests());
                    reservationRepository.save(existingReservation);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservation.getId()));
    }

    public List<Reservation> retrieveAllReservations(){return reservationRepository.findAll();}
}
