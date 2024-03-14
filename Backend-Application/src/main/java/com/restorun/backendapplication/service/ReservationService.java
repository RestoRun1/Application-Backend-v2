package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Reservation;
import com.restorun.backendapplication.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Reservation> retrieveReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public boolean saveReservation(Reservation reservation) {
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
                    existingReservation.setTables(existingReservation.getTables());
                    existingReservation.setNumberOfGuests(existingReservation.getNumberOfGuests());
                    existingReservation.setSpecialRequests(existingReservation.getSpecialRequests());
                    reservationRepository.save(existingReservation);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservation.getId()));
    }

    public List<Reservation> retrieveAllReservations(){return reservationRepository.findAll();}
}
