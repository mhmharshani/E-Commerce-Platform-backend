package edu.icet.ecom.mapper;

import edu.icet.ecom.model.Category;
import edu.icet.ecom.model.Product;
import edu.icet.ecom.model.ShippingAddress;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//Used for Select Queries
public class AddressRowMapper implements RowMapper<ShippingAddress> {

    @Override
    public ShippingAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShippingAddress address = new ShippingAddress();

        address.setId(UUID.fromString(rs.getString("id")));
        address.setFullName(rs.getString("full_name"));
        address.setPhoneNumber(rs.getString("phone_number"));
        address.setAddressLine1(rs.getString("address_line1"));
        address.setAddressLine2(rs.getString("address_line2"));
        address.setCity(rs.getString("city"));
        address.setDistrict(rs.getString("district"));
        address.setPostalCode(rs.getString("postal_code"));
        address.setCountry(rs.getString("country"));
        address.setUserId(UUID.fromString(rs.getString("user_id")));
        address.setIsDefault(rs.getBoolean("is_default"));

        return address;
    }
}
