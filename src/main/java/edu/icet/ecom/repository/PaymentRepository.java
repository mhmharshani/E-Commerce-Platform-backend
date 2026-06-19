package edu.icet.ecom.repository;

import edu.icet.ecom.model.Order;
import edu.icet.ecom.model.Payment;

import java.util.UUID;

public interface PaymentRepository {

    int save(Payment payment);

    Payment findPaymentByOrderId(UUID orderId);
}
