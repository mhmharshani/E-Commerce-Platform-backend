package edu.icet.ecom.model.dto.response;

import lombok.Builder;

@Builder
public record CartItemCountResponse(
        Integer itemCount
) {}
