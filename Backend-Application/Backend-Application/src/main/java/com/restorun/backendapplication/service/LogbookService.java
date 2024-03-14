package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Logbook;
import com.restorun.backendapplication.repository.LogbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LogbookService {
    private final LogbookRepository logbookRepository;
    @Autowired
    public LogbookService(LogbookRepository logbookRepository) {
        this.logbookRepository = logbookRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Logbook> retrieveLogbookById(Long id) {
        return logbookRepository.findById(id);
    }

    public boolean saveLogbook(Logbook logbook) {
        logbookRepository.save(logbook);
        return true;
    }

    public boolean deleteLogbook(Optional<Logbook> logbook) {
        if (logbook.isPresent()){
            logbookRepository.delete(logbook.get());
            return true;
        }
        return true;
    }
    public boolean updateLogbook(Logbook logbook) {
        return logbookRepository.findById(logbook.getId())
                .map(existingLogbook -> {
                    existingLogbook.setBillsTaken(logbook.getBillsTaken());
                    existingLogbook.setEndShift(logbook.getEndShift());
                    existingLogbook.setStartShift(logbook.getStartShift());
                    existingLogbook.setOrdersDelivered(logbook.getOrdersDelivered());
                    logbookRepository.save(existingLogbook);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Logbook not found with id: " + logbook.getId()));
    }
}
