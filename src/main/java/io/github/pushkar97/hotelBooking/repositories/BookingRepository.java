package io.github.pushkar97.hotelBooking.repositories;

import io.github.pushkar97.hotelBooking.models.Booking;
import io.github.pushkar97.hotelBooking.models.Hotel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {

    @Query("select AVG(r.rating) from Booking b JOIN b.hotel h JOIN b.review r where h = :hotel")
    Float ratingForHotel(Hotel hotel);
}
