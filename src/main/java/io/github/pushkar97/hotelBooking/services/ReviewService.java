package io.github.pushkar97.hotelBooking.services;

import io.github.pushkar97.hotelBooking.errors.EntityNotFoundException;
import io.github.pushkar97.hotelBooking.models.Review;
import io.github.pushkar97.hotelBooking.repositories.ReviewRepository;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    ReviewRepository reviewRepository;
    BookingService bookingService;
    HotelService hotelService;

    ReviewService(ReviewRepository reviewRepository,
                  BookingService bookingService,
                  HotelService hotelService) {
        this.reviewRepository = reviewRepository;
        this.bookingService = bookingService;
        this.hotelService = hotelService;
    }

    public Page<Review> all(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public Review one(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Review.class, "Id", id.toString()));
    }

    public Review save(Review review, Long bookingId) {
        var booking = bookingService.one(bookingId);
        review.setBooking(booking);
        //restrict rating between 0 and 10
        byte rating = (byte) (review.getRating() < 0 ? 0 : Math.min(review.getRating(), 10));
        review.setRating(rating);
        var reviewInDb = reviewRepository.save(review);
        var hotelRating = bookingService.getHotelRating(booking.getHotel());
        hotelService.setRating(booking.getHotel().getId(), hotelRating);
        return reviewInDb;
    }

    public Review update(Review review, Long id) {
        review.setId(id);
        var hotel = review.getBooking().getHotel();
        hotelService.setRating(hotel.getId(), bookingService.getHotelRating(hotel));
        return  reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
