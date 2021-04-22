package io.github.pushkar97.hotelBooking.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String addressLine1;

    String addressLine2;

    String city;

    String state;

    String country;

    int zipCode;
}
