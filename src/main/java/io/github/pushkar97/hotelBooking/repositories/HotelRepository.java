package io.github.pushkar97.hotelBooking.repositories;

import io.github.pushkar97.hotelBooking.models.Hotel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @Query("select SUM(b.rooms) from Booking b JOIN b.hotel h where h = :hotel and (b.checkInDate<=:checkInDate or b.checkOutDate >= :checkoutDate)")
    Optional<Integer> bookedRoomsCountForHotelForDate(Hotel hotel, LocalDate checkInDate, LocalDate checkoutDate);

}
