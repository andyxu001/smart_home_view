package com.andy.home.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@ApiModel("登录历史")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistory {

    private Integer id;

    private Integer userId;

    private String nickName;

    @ApiModelProperty("用户操作类型:登录or退出")
    private String opType;

    private String ip;

    private Date opTime;
}
