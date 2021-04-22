package io.github.pushkar97.hotelBooking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class User {

    public enum Role {USER, ADMIN, OWNER}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Email
    private String username;

//    @JsonIgnore
    @ToString.Exclude
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    Boolean expired;

    @ToString.Exclude
    Boolean locked;

    @ToString.Exclude
    Boolean disabled;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Booking> bookings = new ArrayList<>();
}
