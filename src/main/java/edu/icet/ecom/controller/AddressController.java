package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.request.CreateAddressRequest;
import edu.icet.ecom.model.dto.response.CreateAddressResponse;
import edu.icet.ecom.service.AddressService;
import edu.icet.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/user/address")
@RequiredArgsConstructor
public class AddressController {

    private final UserService userService;
    private final AddressService addressService;

    @PostMapping
    public CreateAddressResponse createShippingAddress(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateAddressRequest request) {
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        return addressService.createShippingAddress(userId, request);
    }

}
