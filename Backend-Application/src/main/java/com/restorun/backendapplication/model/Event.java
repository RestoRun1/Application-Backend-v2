package com.restorun.backendapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Event() {
        // Default constructor
    }

    // Parameterized constructor for creating an event instance
    public Event(String title, String description, LocalDateTime startTime, LocalDateTime endTime, Restaurant restaurant) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.restaurant = restaurant;
    }

    public void updateEvent(Event event) {
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.endTime = event.getEndTime();
        this.startTime = event.getStartTime();
        this.restaurant = event.getRestaurant();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @JsonProperty("restaurantId")
    public Long getRestaurantId() {
        return restaurant.getId();
    }
}
