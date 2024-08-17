package com.medical.model.medicine;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 1:38
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import  javax.validation.constraints.DecimalMin;
import  javax.validation.constraints.Min;
import  javax.validation.constraints.NotEmpty;
import  javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Medicine {
    @NotNull
    private Long id; // 药品ID

    @NotEmpty
    private String name; // 药品名称

    private String efficacy; // 功效

    private String ingredients; // 成分

    private String specifications; // 规格

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price; // 价格

    private String usage; // 使用方式
    @JsonFormat(pattern = "yyy-MM-dd")
    private LocalDate productionDate; // 生产日期
    @JsonFormat(pattern = "yyy-MM-dd")
    private LocalDate expirationDate; // 有效期

    @URL
    private String imageUrl; // 图片地址

    @NotNull
    @Min(0)
    private Integer stock; // 库存信息
}