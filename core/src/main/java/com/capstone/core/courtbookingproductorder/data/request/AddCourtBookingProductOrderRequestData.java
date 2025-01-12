package com.capstone.core.courtbookingproductorder.data.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCourtBookingProductOrderRequestData {
    private Long total;
    private Long centerId;
    private List<CartItem> cart;
    private Long courtBookingId;

    @Data
    public static class CartItem {
        private Long productInventoryId;
        private Long quantity;
    }
}
