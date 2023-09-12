package com.andy.home.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("物品头像")
@Data
@ToString
public class ProductAvatar {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("编号")
    private Long code;

    @ApiModelProperty("图片原始名称")
    private String originalName;

    @ApiModelProperty("图片类型")
    private String fileType;

    @ApiModelProperty("存储路径")
    private String filePath;

    @ApiModelProperty("状态")
    private Integer status;
}
