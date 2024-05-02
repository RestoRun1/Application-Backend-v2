/*
package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.TableSeat;
import com.restorun.backendapplication.repository.TableSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TableSeatService {
    private final TableSeatRepository tableSeatRepository;

    @Autowired
    public TableSeatService(TableSeatRepository tableSeatRepository) {
        this.tableSeatRepository = tableSeatRepository;
    }

    @Transactional(readOnly = true)
    public Optional<TableSeat> retrieveTableSeatById(Long id) {
        return tableSeatRepository.findById(id);
    }

    public boolean saveTableSeat(TableSeat tableSeat) {
        tableSeatRepository.save(tableSeat);
        return true;
    }

    public boolean deleteTableSeat(Optional<TableSeat> tableSeat) {
        if (tableSeat.isPresent()){
            tableSeatRepository.delete(tableSeat.get());
            return true;
        }
        return true;
    }

    public List<TableSeat> retrieveAllTableSeats() {
        return tableSeatRepository.findAll();
    }

    public boolean updateTableSeat(TableSeat tableSeat) {
        return tableSeatRepository.findById(tableSeat.getId())
                .map(existingTableSeat -> {
                    existingTableSeat.setDiningTable(existingTableSeat.getDiningTable());
                    existingTableSeat.setSeatNumber(existingTableSeat.getSeatNumber());
                    tableSeatRepository.save(existingTableSeat);

                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("TableSeat not found with id: " + tableSeat.getId()));
    }
}
*/
