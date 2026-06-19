package edu.icet.ecom.mapper;

import edu.icet.ecom.enums.PaymentMethod;
import edu.icet.ecom.enums.PaymentStatus;
import edu.icet.ecom.model.OrderItems;
import edu.icet.ecom.model.Payment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//Used for Select Queries
public class PaymentRowMapper implements RowMapper<Payment> {

    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Payment payment = new Payment();
        payment.setId(UUID.fromString(rs.getString("id")));
        payment.setAmount(rs.getDouble("amount"));
        payment.setPaidAt(rs.getTimestamp("paid_at").toLocalDateTime());
        payment.setTransactionRef(UUID.fromString(rs.getString("transaction_ref")));
        payment.setPaymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")));
        payment.setPaymentStatus(PaymentStatus.valueOf(rs.getString("payment_status")));
        payment.setOrderId(UUID.fromString(rs.getString("order_id")));

        return payment;
    }
}
