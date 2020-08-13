package com.cherny.loftmoney.balance;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cherny.loftmoney.LoftApp;
import com.cherny.loftmoney.R;
import com.cherny.loftmoney.remote.MoneyApi;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagramFragment extends Fragment {

    private MoneyApi moneyApi;
    private TextView myExpences;
    private TextView myIncome;
    private TextView totalFinances;
    private BalanceView mBalanceView;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moneyApi = ((LoftApp) getActivity().getApplication()).getMoneyApi();
        loadBalance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, null);
        myExpences = view.findViewById(R.id.expense);
        myIncome = view.findViewById(R.id.income);
        totalFinances = view.findViewById(R.id.total_finances);
        mBalanceView = view.findViewById(R.id.balanceView);
        mSwipeRefreshLayout = view.findViewById(R.id.balance_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                loadBalance();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final BalanceView balanceView = view.findViewById(R.id.balanceView);

        balanceView.update(new Random().nextInt(), new Random().nextInt());
    }

    public void loadBalance() {
        String token = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(LoftApp.TOKEN_KEY, "");
        final Call<BalanceResponse> responceCall = moneyApi.getBalance(token);
        responceCall.enqueue(new Callback<BalanceResponse>() {

            @Override
            public void onResponse(
                    final Call<BalanceResponse> call, final Response<BalanceResponse> response
            ) {

                final int totalExpences = response.body().getTotalExpences();
                final int totalIncome = response.body().getTotalIncome();

                myExpences.setText(String.valueOf(totalExpences));
                myIncome.setText(String.valueOf(totalIncome));
                totalFinances.setText(String.valueOf(totalIncome - totalExpences));
                mBalanceView.update(totalExpences, totalIncome);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(final Call<BalanceResponse> call, final Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public static DiagramFragment getInstance() { return new DiagramFragment();}
}
