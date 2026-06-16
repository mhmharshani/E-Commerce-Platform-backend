package edu.icet.ecom.repository;

import edu.icet.ecom.model.Payment;

public interface PaymentRepository {

    int save(Payment payment);
}
