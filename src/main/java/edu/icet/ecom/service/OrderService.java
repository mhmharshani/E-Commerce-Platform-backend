package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.request.CheckoutRequest;
import edu.icet.ecom.model.dto.response.CheckoutResponse;

import java.util.UUID;

public interface OrderService {

    CheckoutResponse checkout(UUID userId, CheckoutRequest request);
}
