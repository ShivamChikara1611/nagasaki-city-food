package com.shivamchikara.Nagasaki.City.Food.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    private String name;
    private String description;
    private String cuisineType;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String openingHours;


    @JsonIgnore
    @OneToMany(mappedBy="restaurant",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Order> orders=new ArrayList<>();

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registrationDate;

    private boolean open = true;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private List<Food> foods=new ArrayList<>();


}
