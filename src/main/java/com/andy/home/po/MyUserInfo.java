package com.andy.home.po;

import com.qq.connect.QQConnect;
import com.qq.connect.QQConnectException;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.http.PostParameter;

public class MyUserInfo extends QQConnect {

    private static final long serialVersionUID = -6124397423510235640L;

    public MyUserInfo(String token, String openID) {
        super(token, openID);
    }

    private MyUserInfoBean getUserInfo(String openid) throws QQConnectException {
        return new MyUserInfoBean(this.client.get(QQConnectConfig.getValue("getUserInfoURL"), new PostParameter[]{new PostParameter("openid", openid), new PostParameter("oauth_consumer_key", QQConnectConfig.getValue("app_ID")), new PostParameter("access_token", this.client.getToken()), new PostParameter("format", "json")}).asJSONObject());
    }

    public MyUserInfoBean getUserInfo() throws QQConnectException {
        return this.getUserInfo(this.client.getOpenID());
    }
}
