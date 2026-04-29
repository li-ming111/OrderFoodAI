package com.SIM.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderItemRequest {

    @Schema(description = "菜品ID")
    private Integer dishId;

    @Schema(description = "菜品数量")
    private Integer quantity;

    @Schema(description = "菜品特殊要求")
    private String specialInstructions;
}