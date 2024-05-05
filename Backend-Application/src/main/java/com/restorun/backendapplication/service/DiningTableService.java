package com.restorun.backendapplication.service;

import com.restorun.backendapplication.dto.DiningTableDTO;
import com.restorun.backendapplication.model.DiningTable;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.repository.DiningTableRepository;
import com.restorun.backendapplication.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DiningTableService {
    private final DiningTableRepository diningTableRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DiningTableService(DiningTableRepository diningTableRepository, RestaurantRepository restaurantRepository) {
        this.diningTableRepository = diningTableRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public boolean saveDiningTable(Integer tableNumber, Integer seatingCapacity, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        DiningTable diningTable = new DiningTable();
        diningTable.setTableNumber(tableNumber);
        diningTable.setSeatingCapacity(seatingCapacity);
        diningTable.setRestaurant(restaurant);

        diningTableRepository.save(diningTable);
        return true;
    }

    @Transactional
    public boolean deleteDiningTable(Long id) {
        boolean exists = diningTableRepository.existsById(id);

        if (!exists) {
            throw new RuntimeException("Dining table with id " + id + " does not exist");
        }
        diningTableRepository.deleteById(id);
        return true;
    }

    public Optional<DiningTable> retrieveDiningTableById(Long id) {
        return diningTableRepository.findById(id);
    }

    public boolean updateDiningTable(DiningTable diningTable) {
        return diningTableRepository.findById(diningTable.getId())
                .map(existingDiningTable -> {
                    existingDiningTable.setSeatingCapacity(existingDiningTable.getSeatingCapacity());
                    existingDiningTable.setTableNumber(existingDiningTable.getTableNumber());
                    diningTableRepository.save(existingDiningTable);

                    return true; // Indicates success
                })
                .orElse(false); // Indicates failure
    }

    public List<DiningTable> retrieveAllDiningTables() {
        return diningTableRepository.findAll();
    }

    public DiningTable findOrCreateTable(DiningTableDTO tableDTO) {
        DiningTable table = diningTableRepository.findById(tableDTO.getTableId()).orElse(null);
        if (table == null) {
            table = new DiningTable();
            table.setTableNumber(tableDTO.getTableNumber());
            table.setSeatingCapacity(tableDTO.getSeatingCapacity());
            diningTableRepository.save(table);
        }
        return table;
    }
}
