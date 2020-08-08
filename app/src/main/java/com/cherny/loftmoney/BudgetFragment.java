package com.cherny.loftmoney;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetFragment extends Fragment implements ItemsAdapterListener, ActionMode.Callback {
    public static final int REQUEST_CODE = 100;
    private static final String COLOR_ID = "colorId";
    private static final String TYPE = "fragmentType";
    public MoneyAdapter moneyAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ActionMode mActionMode;
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
        moneyAdapter.setListener(this);
        recyclerView.setAdapter(moneyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
             LinearLayoutManager.VERTICAL, false));

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
                        loadItems();
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
                ((MainActivity)getActivity()).loadBalance();
            }

            @Override
            public void onFailure(final Call<List<MoneyCellModel>> call, final Throwable t) {

            }
        });

    }

    @Override
    public void onItemClick(MoneyCellModel moneyCellModel, int position) {

        if (mActionMode != null) {
            moneyAdapter.toggleItem(position);
            mActionMode.setTitle(getString(R.string.selected, String.valueOf(moneyAdapter.getSelectedSize())));
        }
    }

    @Override
    public void onItemLongClick(MoneyCellModel moneyCellModel, int position) {
        if (mActionMode == null) {
            getActivity().startActionMode(this);
        }
        moneyAdapter.toggleItem(position);
        if (mActionMode != null) {
            mActionMode.setTitle(getString(R.string.selected, String.valueOf(moneyAdapter.getSelectedSize())));
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        mActionMode = actionMode;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getActivity());
        menuInflater.inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.remove) {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.confirmation)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            removeItems();
                            mActionMode.finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }
        return true;
    }

    private void removeItems() {
        String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString((LoftApp.TOKEN_KEY), "");
        List<Integer> selectedItems = moneyAdapter.getSelectedItemsId();
        for (Integer itemId: selectedItems) {
            Call<AuthResponse> call = moneyApi.removeItem(String.valueOf(itemId.intValue()), token);
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    loadItems();
                    moneyAdapter.clearSelections();
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        mActionMode = null;
        moneyAdapter.clearSelections();
    }
}
