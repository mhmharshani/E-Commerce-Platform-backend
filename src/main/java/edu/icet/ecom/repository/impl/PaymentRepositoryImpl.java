package edu.icet.ecom.repository.impl;

import edu.icet.ecom.mapper.PaymentRowMapper;
import edu.icet.ecom.model.Payment;
import edu.icet.ecom.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JdbcTemplate template;

    @Override
    public int save(Payment payment) {
        String sql = """
                INSERT INTO payments
                (id, amount, paid_at, transaction_ref, payment_method, payment_status, order_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        return template.update(
                sql,
                payment.getId().toString(),
                payment.getAmount(),
                payment.getPaidAt(),
                payment.getTransactionRef().toString(),
                payment.getPaymentMethod().name(),
                payment.getPaymentStatus().name(),
                payment.getOrderId().toString()
        );
    }

    @Override
    public Payment findPaymentByOrderId(UUID orderId) {
        String sql = """
                SELECT *
                FROM payments
                WHERE order_id = ?""";
        try {
            return template.queryForObject(
                    sql,
                    new PaymentRowMapper(),
                    orderId.toString()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
