package com.cherny.loftmoney.remote;

import com.google.gson.annotations.SerializedName;

public class MoneyItem {
    @SerializedName("id") private String itemId;
    @SerializedName("name") private String name;
    @SerializedName("price") private int price;
    @SerializedName("type") private String type;

    public MoneyItem(String name, String type,int price) {
        this.name = name;
        this.type = type;
        this.price = price;

    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    @SerializedName("data") String data;
}
