package com.SIM.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema
@Data
@TableName("order_item")
public class OrderItem {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "菜品")
    private String dishName;

    @Schema(description = "订单")
    private Integer orderId;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "特殊要求")
    private String specialInstructions;
}