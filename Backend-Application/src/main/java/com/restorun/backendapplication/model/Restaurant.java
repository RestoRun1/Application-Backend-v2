package com.restorun.backendapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Meal> meals;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Manager> managers = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

   /* @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DiningTable> diningTables = new HashSet<>();*/

    // TODO: annotation transient will be changed
    @Setter
    @Getter
    @Transient
    private String occupancy;

    // Constructors, getters, setters, and other methods

    /*
    public boolean addEmployee(Employee employee) {
        try{
            employees.add(employee);
            employee.setRestaurant(this);
            return true;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }
    public boolean removeEmployee(Employee employee) {
        try{
            employees.remove(employee);
            employee.setRestaurant(null);
            return true;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }
    public void saveEvent(Event event) {
        events.add(event);
        event.setRestaurant(this);
    }
    public void removeEvent(Event event) {
        events.remove(event);
        event.setRestaurant(null);
    }

    public boolean addItemToMenu(MenuItem item) {
        try{
            menu.addItem(item);
            return true;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }

    public boolean removeItemFromMenu(MenuItem item) {
        boolean bool;
        try{
            bool = menu.removeItem(item);
            return bool;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }
    */

    // Constructors
    // Parameterized constructor
    public Restaurant(Long id, String name, String address, String phoneNumber, Set<Employee> employees) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.employees = employees;
    }

    // Constructor without id
    // for saving the restaurant to the database for the first time
    public Restaurant(String name, String address, String phoneNumber, Set<Employee> employees) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.employees = employees;
    }

    public Restaurant() {
        // default constructor
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '\"' +
                ", \"address\":\"" + address + '\"' +
                ", \"phoneNumber\":\"" + phoneNumber + '\"' +
                '}';
    }
}
