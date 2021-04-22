package io.github.pushkar97.hotelBooking.repositories;

import io.github.pushkar97.hotelBooking.models.Review;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {
}
