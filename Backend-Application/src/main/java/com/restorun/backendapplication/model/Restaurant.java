package com.restorun.backendapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Restaurant {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(nullable = false)
    private String address;

    @Setter
    @Getter
    @Column(nullable = false)
    private String phoneNumber;

    @Setter
    @Getter
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events = new HashSet<>();

    // Constructors, getters, setters, and other methods
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setRestaurant(this);
    }
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setRestaurant(null);
    }
    public void addEvent(Event event) {
        events.add(event);
        event.setRestaurant(this);
    }

    public void removeEvent(Event event) {
        events.remove(event);
        event.setRestaurant(null);
    }

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
