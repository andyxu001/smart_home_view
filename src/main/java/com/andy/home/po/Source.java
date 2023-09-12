package com.andy.home.po;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Alias("source")
@ApiModel("来源")
public class Source {

    private Integer id;

    private String sourceName;

    private Integer userId;

    private String remark;

}
