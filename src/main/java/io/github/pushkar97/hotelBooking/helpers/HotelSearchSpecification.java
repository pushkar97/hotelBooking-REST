package io.github.pushkar97.hotelBooking.helpers;

import io.github.pushkar97.hotelBooking.models.Facilities;
import io.github.pushkar97.hotelBooking.models.Hotel;
import io.github.pushkar97.hotelBooking.models.SortType;
import io.github.pushkar97.hotelBooking.services.HotelService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class HotelSearchSpecification {

    HotelService hotelService;

    public HotelSearchSpecification(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public Specification<Hotel> search(Optional<String> city
            , Optional<LocalDate> checkInDate
            , Optional<LocalDate> checkOutDate
            , Optional<Integer> rooms
            , Optional<Integer> adults
            , Optional<Integer> children
            , Optional<Byte> rating
            , Optional<List<Facilities>> facilities
            , Optional<SortType> sortBy) {

        return (root, criteriaQuery,  criteriaBuilder) -> {
            Predicate p = criteriaBuilder.disjunction();
            city.ifPresent(c -> p.getExpressions()
                    .add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("address").get("city")), c.toLowerCase())));

            rating.ifPresent(r -> p.getExpressions()
                    .add(criteriaBuilder.ge(root.get("rating"), r)));
            return p;
        };

    }



}
