package com.cherny.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.cherny.loftmoney.cells.money.MoneyAdapter;
import com.cherny.loftmoney.cells.money.MoneyAdapterClick;
import com.cherny.loftmoney.cells.money.MoneyCellModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MoneyAdapter moneyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        moneyAdapter = new MoneyAdapter();
        moneyAdapter.setMoneyAdapterClick(new MoneyAdapterClick() {
            @Override
            public void onMoneyClick(MoneyCellModel moneyCellModel) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(moneyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));

        moneyAdapter.setData(generateExpenses());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moneyAdapter.addData(generateIncomes());
                moneyAdapter.addData(generateIncomes());
                moneyAdapter.addData(generateIncomes());
                moneyAdapter.addData(generateIncomes());
            }
        }, 3000);
    }

    private List<MoneyCellModel> generateExpenses() {
        List<MoneyCellModel> moneyCellModels = new ArrayList<>();
        moneyCellModels.add(new MoneyCellModel("Молоко", "70 Р", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Зубная щётка", "70 Р", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Сковородка с антипригарным покрытием",
                "1670 Р", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Сковородка с антипригарным покрытием",
                "1670 Р", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Сковородка с антипригарным покрытием",
                "1670 Р", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Сковородка с антипригарным покрытием",
                "1670 Р", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Сковородка с антипригарным покрытием",
                "1670 Р", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Сковородка с антипригарным покрытием",
                "1670 Р", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Сковородка с антипригарным покрытием",
                "1670 Р", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Сковородка с антипригарным покрытием",
                "1670 Р", R.color.expenseColor));



        return moneyCellModels;
    }


    private List<MoneyCellModel> generateIncomes() {
        List<MoneyCellModel> moneyCellModels = new ArrayList<>();
        moneyCellModels.add(new MoneyCellModel("Зарплата.Июнь", "70000 Р", R.color.incomeColor));
        moneyCellModels.add(new MoneyCellModel("Премия", "7000 Р", R.color.incomeColor));
        moneyCellModels.add(new MoneyCellModel("Олег наконец-то вернул долг",
                "300000 Р", R.color.incomeColor));

        return moneyCellModels;
    }
}



// TODO: - HEre we will work with RecycleView