package com.cherny.loftmoney.cells.money;

import com.cherny.loftmoney.R;
import com.cherny.loftmoney.remote.MoneyItem;

public class MoneyCellModel {
    private String name;
    private int price;
    private int color;

    public MoneyCellModel(String name, int price, int color) {
        this.name = name;
        this.price = price;
        this.color = color;
    }

    public static MoneyCellModel getInstance(MoneyItem moneyItem) {
        return new MoneyCellModel(moneyItem.getName(), moneyItem.getPrice(), moneyItem.getType().equals("expense") ? R.color.expenseColor : R.color.incomeColor);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getColor() {
        return color;
    }
}
