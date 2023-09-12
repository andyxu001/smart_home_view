package com.andy.home.po.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("物品查询参数")
@Data
public class ProductDto {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("用户")
    private Integer userId;

    @ApiModelProperty("查询物品名称")
    private String name;

    @ApiModelProperty("类型:(管理数据字典中的编号)")
    private Long type;

    @ApiModelProperty("查询生产日：起始")
    private String productivityDateStart;

    @ApiModelProperty("查询生产日：截至")
    private String productivityDateEnd;

    @ApiModelProperty("查询过期日:起始")
    private String expireDateStart ;

    @ApiModelProperty("过期日:截至")
    private String expireDateEnd;

    @ApiModelProperty("0：正常  -1:异常")
    private Integer status;

    @ApiModelProperty("距过期剩余天数")
    private Integer diffDay;

    @ApiModelProperty("处理状态")
    private Integer isProcess;

}
