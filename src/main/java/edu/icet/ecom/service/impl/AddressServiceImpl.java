package edu.icet.ecom.service.impl;

import edu.icet.ecom.model.ShippingAddress;
import edu.icet.ecom.model.dto.request.CreateAddressRequest;
import edu.icet.ecom.model.dto.response.CreateAddressResponse;
import edu.icet.ecom.repository.AddressRepository;
import edu.icet.ecom.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public CreateAddressResponse createShippingAddress(UUID userId, CreateAddressRequest request) {

        ShippingAddress address = ShippingAddress.builder()
                .id(UUID.randomUUID())
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .district(request.getDistrict())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .isDefault(true)
                .build();


        return CreateAddressResponse.builder()
                .id(address.getId())
                .fullName(address.getFullName())
                .phoneNumber(address.getPhoneNumber())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .district(address.getDistrict())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .isDefault(address.getIsDefault())
                .build();
    }
}