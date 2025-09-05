package com.shivamchikara.Nagasaki.City.Food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shivamchikara.Nagasaki.City.Food.domain.USER_ROLE;
import com.shivamchikara.Nagasaki.City.Food.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private USER_ROLE role;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @ElementCollection
    private List<RestaurantDto> favorites=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
}
