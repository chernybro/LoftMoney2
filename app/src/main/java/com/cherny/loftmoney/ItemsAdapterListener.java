package com.cherny.loftmoney;

import com.cherny.loftmoney.cells.money.MoneyCellModel;

public interface ItemsAdapterListener {
    public void onItemClick(MoneyCellModel moneyCellModel, int position);

    public void onItemLongClick(MoneyCellModel moneyCellModel, int position);
}
