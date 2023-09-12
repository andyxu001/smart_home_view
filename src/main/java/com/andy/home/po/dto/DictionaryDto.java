package com.andy.home.po.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictionaryDto implements Serializable {

    private Integer pageNum = 1;

    private Integer pageSize = 1000;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("用户主键")
    private Integer userId;

    @ApiModelProperty("编号")
    private Long code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("0：正常  -1:异常")
    private Integer status;

    @ApiModelProperty("根据此字段判定")
    private String type;

}
