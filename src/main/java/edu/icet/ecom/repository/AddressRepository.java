package edu.icet.ecom.repository;

import edu.icet.ecom.model.ShippingAddress;

import java.util.UUID;

public interface AddressRepository {

    int save(ShippingAddress address);

    ShippingAddress findById(UUID shippingAddressId);
}
