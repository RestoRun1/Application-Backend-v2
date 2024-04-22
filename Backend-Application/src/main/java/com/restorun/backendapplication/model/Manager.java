package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.Role;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Manager extends Employee { // Assuming Manager extends from Employee

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Manager() {
        super();
        this.role = Role.MANAGER;
    }

    /*
    public boolean addEmployee(Employee employee){
        try{
            return restaurant.addEmployee(employee);
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }
    public boolean removeEmployee(Employee employee){
        try{
            return restaurant.removeEmployee(employee);
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }
    // TODO
    public boolean modifyEmployee(Employee employee){
        return false;
    }

    // TODO: MODIFY MENUITEM TO ITEM AFTER ABSTRACT DESIGN PATTERN IS IMPLEMENTED
    //       MANAGER SHOULD BE ABLE TO HAVE ACCESS TO BOTH MENU AND MEAL ITEMS
    public boolean addItem(MenuItem item){
        try{
            restaurant.addItemToMenu(item);
            return true;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }

    public boolean removeItem(MenuItem item){
        try{
            restaurant.removeItemFromMenu(item);
            return true;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }

    // TODO
    public boolean modifyItem(MenuItem item){
        return false;
    }

    public boolean saveEvent(Event event){
        try{
            restaurant.saveEvent(event);
            return true;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }

    public boolean removeEvent(Event event){
        try{
            restaurant.removeEvent(event);
            return true;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }

    // TODO
    public boolean modifyEvent(Event event){
        return false;
    }

    // TODO: MAKE PARAMETERS -DEFAULT -HAVE INITIAL VALUE -REQUIRED ETC.
    //       USE @REQUESTMAPPING
    public boolean manageReservations(Reservation reservation){

        return false;
    }

    public String checkOccupancy(){
        String occupancy;
        try {
            occupancy = restaurant.getOccupancy();
        }
        catch (Exception e){
            throw new NullPointerException();
        }
        return occupancy;
    }

    */

    // Constructors, getters, and setters are managed by Lombok annotations
}
