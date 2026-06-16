package edu.icet.ecom.repository.impl;

import edu.icet.ecom.model.Payment;
import edu.icet.ecom.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JdbcTemplate template;

    @Override
    public int save(Payment payment) {
        String sql = """
                INSERT INTO payment
                (id, order_id, amount, payment_method, payment_status, created_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        return template.update(
                sql,
                payment.getId().toString(),
                payment.getOrderId().toString(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getPaidAt()
        );
    }
}
