package com.SIM.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class OrderRequest {

    @Schema(description = "菜品详情")
    private List<OrderItemRequest> items;

    @Schema(description = "客户姓名")
    private String customerName;

    @Schema(description = "客户手机号")
    private String customerPhone;

    @Schema(description = "配送地址")
    private String deliveryAddress;
}