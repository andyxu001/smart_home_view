package com.andy.home.po;

import com.qq.connect.QQConnectException;
import com.qq.connect.QQConnectResponse;
import com.qq.connect.javabeans.Avatar;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.utils.json.JSONException;
import com.qq.connect.utils.json.JSONObject;

import java.io.Serializable;

public class MyUserInfoBean extends QQConnectResponse implements Serializable {

    private static final long serialVersionUID = 5606709876246698659L;

    private Avatar avatar = new Avatar("");
    private String nickname;
    private String gender;
    private boolean vip;
    private int level;
    private boolean yellowYearVip;
    private int ret;
    private String msg;

    private MyAvatar myAvatar = new MyAvatar();
    private String openId;
    //qq头像
    private String avatarForQQ;
    //qq空间头像
    private String avatarForQQZone;

    private String province;
    private String city;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAvatarForQQ() {
        return avatarForQQ;
    }

    public void setAvatarForQQ(String avatarForQQ) {
        this.avatarForQQ = avatarForQQ;
    }

    public String getAvatarForQQZone() {
        return avatarForQQZone;
    }

    public void setAvatarForQQZone(String avatarForQQZone) {
        this.avatarForQQZone = avatarForQQZone;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String name){
        this.nickname = name;
    }

    public String getGender() {
        return this.gender;
    }

    public boolean isVip() {
        return this.vip;
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isYellowYearVip() {
        return this.yellowYearVip;
    }

    public int getRet() {
        return this.ret;
    }

    public String getMsg() {
        return this.msg;
    }

    public MyUserInfoBean(JSONObject json) throws QQConnectException {
        this.init(json);
    }

    public MyUserInfoBean(String nickname,String avatarForQQ){
        this.nickname = nickname;
        this.avatarForQQ = avatarForQQ;
    }

    private void init(JSONObject json) throws QQConnectException {
        if (json != null) {
            try {
                this.ret = json.getInt("ret");
                if (0 != this.ret) {
                    this.msg = json.getString("msg");
                } else {
                    this.msg = "";
                    this.nickname = json.getString("nickname");
                    this.gender = json.getString("gender");
                    this.vip = json.getInt("vip") == 1;
                    this.avatar = new Avatar(json.getString("figureurl"), json.getString("figureurl_1"), json.getString("figureurl_2"));
                    this.level = json.getInt("level");
                    this.yellowYearVip = json.getInt("is_yellow_year_vip") == 1;
                    this.myAvatar = new MyAvatar(json.getString("figureurl_qq_1"),json.getString("figureurl_qq_2"));
                    this.avatarForQQZone = avatar.getAvatarURL100();
                    this.avatarForQQ = myAvatar.getAvatarQQURL100();
                    this.province = json.getString("province");
                    this.city = json.getString("city");
                }
            } catch (JSONException var3) {
                throw new QQConnectException(var3.getMessage() + ":" + json.toString(), var3);
            }
        }

    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.nickname == null ? 0 : this.nickname.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            MyUserInfoBean other = (MyUserInfoBean)obj;
            if (this.nickname == null) {
                if (other.nickname != null) {
                    return false;
                }
            } else if (!this.nickname.equals(other.nickname)) {
                return false;
            }

            return true;
        }
    }

    @Override
    public String toString() {
        return "QQ用户信息:MyUserInfoBean{" +
                "nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", vip=" + vip +
                ", level=" + level +
                ", openId='" + openId + '\'' +
                ", avatarForQQ='" + avatarForQQ + '\'' +
                ", avatarForQQZone='" + avatarForQQZone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
