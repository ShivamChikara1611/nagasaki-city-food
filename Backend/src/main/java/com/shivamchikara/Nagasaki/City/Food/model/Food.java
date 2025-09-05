package com.shivamchikara.Nagasaki.City.Food.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;


    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    private boolean available = true;
    @ManyToOne
    private Restaurant restaurant;


    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
}
