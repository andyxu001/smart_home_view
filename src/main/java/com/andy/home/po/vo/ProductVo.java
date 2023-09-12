package com.andy.home.po.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("物品显示字段")
@Data
public class ProductVo {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("编号")
    private Long code;

    @ApiModelProperty("物品名称")
    private String name;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("单位编号")
    private Long unit;

    @ApiModelProperty("单位")
    private String unitName;

    @ApiModelProperty("类型编号")
    private Long type;

    @ApiModelProperty("类型")
    private String typeName;

    @ApiModelProperty("生产日")
    private String productivityDate;

    @ApiModelProperty("过期日")
    private String expireDate;

    @ApiModelProperty("0：正常  -1:异常")
    private Integer status;

    @ApiModelProperty("离过期剩余天数")
    private Integer diffDay;

    @ApiModelProperty("物品图片路径")
    private String filePath;

    @ApiModelProperty("物品是否处理")
    private Integer isProcess;
}
