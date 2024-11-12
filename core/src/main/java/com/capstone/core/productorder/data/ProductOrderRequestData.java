package com.capstone.core.productorder.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderRequestData {
    private Long total;
    private Long centerId;
    private List<CartItem> cart;

    @Data
    public static class CartItem {
        private Long productInventoryId;
        private Long quantity;
    }
}
