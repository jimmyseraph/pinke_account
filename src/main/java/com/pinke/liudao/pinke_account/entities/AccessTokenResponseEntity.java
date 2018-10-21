package com.pinke.liudao.pinke_account.entities;

import java.util.Date;

public class AccessTokenResponseEntity {
    private String token;
    private Date expiredTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}
