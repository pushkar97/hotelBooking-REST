package io.github.pushkar97.hotelBooking.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate checkOutDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate bookingDate;

    int adults;

    int children;

    int rooms;

    @ManyToOne(fetch = FetchType.EAGER)
    Hotel hotel;

    @ManyToOne(fetch = FetchType.EAGER)
    User user;

    double price;

    @JsonIgnore
    @OneToOne(mappedBy = "booking")
    Review review;
}
