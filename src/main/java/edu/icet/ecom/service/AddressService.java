package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.request.CreateAddressRequest;
import edu.icet.ecom.model.dto.response.CreateAddressResponse;

import java.util.UUID;

public interface AddressService {

    CreateAddressResponse createShippingAddress(UUID userId, CreateAddressRequest request);
}
