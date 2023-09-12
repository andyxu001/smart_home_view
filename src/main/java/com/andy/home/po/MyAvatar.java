package com.andy.home.po;

public class MyAvatar {

    private static final long serialVersionUID = -8402565179459840812L;

    private String avatarQQURL40;
    private String avatarQQURL100;

    public MyAvatar() {
    }

    public MyAvatar(String avatarQQURL40, String avatarQQURL100) {
        this.avatarQQURL40 = avatarQQURL40;
        this.avatarQQURL100 = avatarQQURL100;
    }

    public String getAvatarQQURL40() {
        return avatarQQURL40;
    }

    public void setAvatarQQURL40(String avatarQQURL40) {
        this.avatarQQURL40 = avatarQQURL40;
    }

    public String getAvatarQQURL100() {
        return avatarQQURL100;
    }

    public void setAvatarQQURL100(String avatarQQURL100) {
        this.avatarQQURL100 = avatarQQURL100;
    }
}
