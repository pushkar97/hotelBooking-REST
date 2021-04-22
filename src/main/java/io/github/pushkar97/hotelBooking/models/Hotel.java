package io.github.pushkar97.hotelBooking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    Address address;

    @ElementCollection(targetClass=Facilities.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="hotel_facilities")
    @Column(name="facility")
    List<Facilities> facilities;

    int totalRooms;

    int travellersPerRoom;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    User owner;

    Float rating;

    @JsonIgnore
    @OneToMany(
            mappedBy = "hotel",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Booking> bookings = new ArrayList<>();
}
