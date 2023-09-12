package com.andy.home.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel("物品")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Alias("product")
public class Product {

    private static final long serialVersionUID = -1;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("用户主键")
    private Integer userId;

    @ApiModelProperty("编号")
    private Long code;

    @ApiModelProperty("物品名称")
    private String name;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("单位:(管理数据字典中的编号)")
    private Long unit;

    @ApiModelProperty("类型:(管理数据字典中的编号)")
    private Long type;

    @ApiModelProperty("生产日")
    private Date productivityDate;

    @ApiModelProperty("过期日")
    private Date expireDate;

    @ApiModelProperty("0：正常  -1:异常")
    private Integer status;

    @ApiModelProperty("物品图片ID")
    private Long fileId;

    @ApiModelProperty("物品是否处理")
    private Integer isProcess;

    @ApiModelProperty("排序字段")
    private Integer orderBy;
}
