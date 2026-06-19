package edu.icet.ecom.repository.impl;

import edu.icet.ecom.mapper.AddressRowMapper;
import edu.icet.ecom.mapper.ProductRowMapper;
import edu.icet.ecom.model.ShippingAddress;
import edu.icet.ecom.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class AddressRepositoryImpl implements AddressRepository {

    private final JdbcTemplate template;

    @Override
    public int save(ShippingAddress address) {
        String sql = """
                INSERT INTO addresses
                (id, full_name, phone_number, address_line1, address_line2, city, district, postal_code, country, user_id, is_default)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        return template.update(
                sql,
                address.getId().toString(),
                address.getFullName(),
                address.getPhoneNumber(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getDistrict(),
                address.getPostalCode(),
                address.getCountry(),
                address.getUserId().toString(),
                address.getIsDefault()
        );
    }

    @Override
    public ShippingAddress findById(UUID shippingAddressId) {
        String sql = """
                SELECT *
                FROM addresses
                WHERE id = ?""";
        try {
            return template.queryForObject(
                    sql,
                    new AddressRowMapper(),
                    shippingAddressId.toString()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
