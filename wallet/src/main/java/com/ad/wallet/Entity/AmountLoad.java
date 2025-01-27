package com.ad.wallet.Entity;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "amountload")
public class AmountLoad {
    private String mobile;
    private String amount;
    private String timestamp;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
