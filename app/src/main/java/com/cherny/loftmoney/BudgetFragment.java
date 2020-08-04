package com.cherny.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cherny.loftmoney.cells.money.MoneyAdapter;
import com.cherny.loftmoney.cells.money.MoneyCellModel;
import com.cherny.loftmoney.remote.AuthResponse;
import com.cherny.loftmoney.remote.MoneyApi;
import com.cherny.loftmoney.remote.MoneyItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetFragment extends Fragment {
    public static final int REQUEST_CODE = 100;
    private static final String COLOR_ID = "colorId";
    private static final String TYPE = "fragmentType";
    public MoneyAdapter moneyAdapter;

    SwipeRefreshLayout mSwipeRefreshLayout;
    private MoneyApi moneyApi;

    public static BudgetFragment newInstance(final int colorId, final String type) {
        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COLOR_ID, colorId);
        bundle.putString(TYPE, type);
        budgetFragment.setArguments(bundle);
        return budgetFragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moneyApi = ((LoftApp)getActivity().getApplication()).getMoneyApi();
        loadItems();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, null);


        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });
        moneyAdapter = new MoneyAdapter(getArguments().getInt(COLOR_ID));
        recyclerView.setAdapter(moneyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
             LinearLayoutManager.VERTICAL, false));

        //generateExpenses();
        //moneyAdapter.setData(generateIncomes());

        return view;
    }


    @Override
    public void onActivityResult(
            final int requestCode, final int resultCode, @Nullable final Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            int price;
            try {
                price = Integer.parseInt(data.getStringExtra("price"));
            } catch (NumberFormatException e) {
                price = 0;
            }
            final int realPrice = price;
            final String name = data.getStringExtra("name");

            final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString((LoftApp.TOKEN_KEY), "");

            Call<AuthResponse> call = moneyApi.addItem(new MoneyItem(name, getArguments().getString(TYPE), price), token);
            call.enqueue(new Callback<AuthResponse>() {

                @Override
                public void onResponse(
                        final Call<AuthResponse> call, final Response<AuthResponse> response
                ) {
                    if (response.body().getStatus().equals("success")) {
                        moneyAdapter.addItem(new MoneyCellModel(name, realPrice, R.color.textColor));
                    }
                }

                @Override
                public void onFailure(final Call<AuthResponse> call, final Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }

    public void loadItems() {
        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString((LoftApp.TOKEN_KEY), "");

        Call<List<MoneyCellModel>> items = moneyApi.getItems(getArguments().getString(TYPE), token);
        items.enqueue(new Callback<List<MoneyCellModel>>() {

            @Override
            public void onResponse(
                    final Call<List<MoneyCellModel>> call, final Response<List<MoneyCellModel>> response
            ) {
                moneyAdapter.clearItems();
                mSwipeRefreshLayout.setRefreshing(false);
                List<MoneyCellModel> items = response.body();
                for (MoneyCellModel item : items) {
                    moneyAdapter.addItem(item);
                }
            }

            @Override
            public void onFailure(final Call<List<MoneyCellModel>> call, final Throwable t) {

            }
        });

    }

    /*public void onActivityResult(
            final int requestCode, final int resultCode, @Nullable final Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);

        int price;
        try {
            price = Integer.parseInt(data.getStringExtra("price"));
        } catch (NumberFormatException e) {
            price = 0;
        }
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            moneyAdapter.addItem(new MoneyCellModel(data.getStringExtra("name"), price, R.color.incomeColor));
        }
    }*/


    private List<MoneyCellModel> generateIncomes() {
        List<MoneyCellModel> moneyCellModels = new ArrayList<>();
        moneyCellModels.add(new MoneyCellModel("Зарплата.Июнь", 70000, R.color.incomeColor));
        moneyCellModels.add(new MoneyCellModel("Премия", 7000, R.color.incomeColor));
        moneyCellModels.add(new MoneyCellModel("Олег наконец-то вернул долг",
                300000, R.color.incomeColor));

        return moneyCellModels;
    }
}
