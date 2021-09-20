package com.example.tacocloud.orders;

import com.example.tacocloud.Taco;
import com.example.tacocloud.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "Taco_Order")
public class Order implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "placedat")
    private Date placedAt;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "deliveryname")
    private String deliveryName;
    @NotEmpty(message = "Street cannot be empty")
    @Column(name = "deliverystreet")
    private String deliveryStreet;
    @NotEmpty(message = "City cannot be empty")
    @Column(name = "deliverycity")
    private String deliveryCity;
    @NotEmpty(message = "State cannot be empty")
    @Column(name = "deliverystate")
    private String deliveryState;
    @NotEmpty(message = "Zip code cannot be empty")
    @Column(name = "deliveryzip")
    private String deliveryZip;
    @CreditCardNumber(message = "Invalid credit card number")
    @Column(name = "ccnumber")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([/])([1-9][0-9])$",
            message="Must be formatted MM/YY")
    @Column(name = "ccexpiration")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    @Column(name = "cccvv")
    private String ccCVV;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Taco> tacos = new ArrayList<>();

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

    @ManyToOne
    private User user;

    public void addDesign(Taco saved) {
        tacos.add(saved);
    }
}