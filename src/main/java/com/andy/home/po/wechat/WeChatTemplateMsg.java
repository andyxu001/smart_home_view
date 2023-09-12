package com.andy.home.po.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatTemplateMsg implements Serializable {

     private String value;

     private String color;

}
