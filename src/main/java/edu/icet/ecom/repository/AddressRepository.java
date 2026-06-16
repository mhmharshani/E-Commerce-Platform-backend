package edu.icet.ecom.repository;

import edu.icet.ecom.model.ShippingAddress;

public interface AddressRepository {

    int save(ShippingAddress address);
}
