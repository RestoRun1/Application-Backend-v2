package com.restorun.backendapplication.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DiningTableTest {
    @Test
    public void testSetter_setsProperly() throws NoSuchFieldException, IllegalAccessException {
        //given
        final DiningTable table = new DiningTable();

        long randValue = (long) (Math.random() * 1000);
        int dummyValue = 10;
        Set<Reservation> reservations = new HashSet<>();
        for (int i = 0; i < dummyValue; i++) {
            Reservation reservation = new Reservation();
        }
        //when
        table.setId(randValue);
        table.setTableNumber(dummyValue);
        table.setSeatingCapacity(dummyValue);

        //then
        assertEquals(randValue, table.getId());
        assertEquals(dummyValue, table.getTableNumber());
        assertEquals(dummyValue, table.getSeatingCapacity());
    }

    @Test
    void getSeatingCapacity() {
    }

    @Test
    void getReservations() {
    }

    @Test
    void setSeatingCapacity() {
    }

    @Test
    void setReservations() {
    }
}