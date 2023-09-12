package com.andy.home.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("账号(用户名)==登录账号")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickName;

    private Integer age;

    private String telephoneNo;

    private String email;

    private String sex;

    private Long avatarId;

    private String avatarUrl;

    @ApiModelProperty("普通用户")
    private String isNormalUser;

    @ApiModelProperty("qq用户")
    private String isQQUser;

    @ApiModelProperty("微信用户")
    private String isWechatUser;

    private Integer status;

}
