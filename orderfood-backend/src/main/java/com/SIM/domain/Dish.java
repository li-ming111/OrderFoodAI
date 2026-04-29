package com.SIM.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Schema
@Data
@TableName("dish")
@Accessors(chain = true)
public class Dish {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema( description = "菜品名称")
    private String name;

    @Schema(description = "菜品描述")
    private String description;

    @Schema( description = "菜品价格")
    private BigDecimal price;

    @Schema(description = "菜品分类")
    private String category;

    @Schema(description = "菜品图片地址")
    private String imageUrl;

    @Schema(description = "菜品是否可用")
    private Boolean available = true;
}