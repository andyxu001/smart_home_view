package com.andy.home.po.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WechatToken{

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expire_in")
    private Integer expireIn;

    @JsonProperty("errmsg")
    private String errMsg;

    @JsonProperty("errcode")
    private Integer errcode;


}
