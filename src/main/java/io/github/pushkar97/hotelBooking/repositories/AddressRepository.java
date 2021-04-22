package io.github.pushkar97.hotelBooking.repositories;

import io.github.pushkar97.hotelBooking.models.Address;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {
}
