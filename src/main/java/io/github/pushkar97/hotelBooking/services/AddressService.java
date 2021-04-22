package io.github.pushkar97.hotelBooking.services;

import io.github.pushkar97.hotelBooking.models.Address;
import io.github.pushkar97.hotelBooking.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }
    public Address update(Address address, Long id) {
        address.setId(id);
        return addressRepository.save(address);
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

}
