package com.ad.wallet.Entity;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Credentials {
    private String mobile;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
