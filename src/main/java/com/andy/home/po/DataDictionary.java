package com.andy.home.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@ApiModel("数据字典")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Alias("dataDictionary")
public class DataDictionary implements Serializable {
    private static final long serialVersionUID = -1;

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

    @ApiModelProperty("排序字段")
    private Integer orderBy;
}
