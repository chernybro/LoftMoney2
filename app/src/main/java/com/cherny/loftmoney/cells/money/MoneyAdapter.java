package com.cherny.loftmoney.cells.money;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cherny.loftmoney.ItemsAdapterListener;
import com.cherny.loftmoney.R;

import java.util.ArrayList;
import java.util.List;



public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder> {

    private List<MoneyCellModel> moneyCellModels = new ArrayList<>();
    private MoneyAdapterClick moneyAdapterClick;
    private ItemsAdapterListener mListener;

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray();

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleItem(final int position) {
        mSelectedItems.put(position, !mSelectedItems.get(position));
        notifyDataSetChanged();
    }


    public int getSelectedSize() {
        int result = 0;
        for(int i = 0; i < moneyCellModels.size(); i++){
            if(mSelectedItems.get(i)) {
                result++;
            }
        }
        return result;
    }

    public List<Integer> getSelectedItemsId() {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        for (MoneyCellModel moneyCellModel: moneyCellModels) {
            if (mSelectedItems.get(i)) {
                result.add(moneyCellModel.getId());
            }
            i++;
        }
        return result;
    }

    private final int colorId;

    public MoneyAdapter(final int colorId) {
        this.colorId = colorId;
    }

    public void setListener(ItemsAdapterListener listener) {
        mListener = listener;
    }


    public void addItem(MoneyCellModel moneyCellModel) {
        moneyCellModels.add(moneyCellModel);
        notifyDataSetChanged();
    }

    public void clearItems() {
        moneyCellModels.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.cell_money, null);

        return new MoneyViewHolder(itemView, colorId);
    }


    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(moneyCellModels.get(position), mSelectedItems.get(position));
        holder.setListener(mListener, moneyCellModels.get(position), position);
    }

    @Override
    public int getItemCount() {
        return moneyCellModels.size();
    }

    static class MoneyViewHolder extends RecyclerView.ViewHolder {
        private View mItemView;
        private TextView nameView;
        private TextView valueView;

        public MoneyViewHolder(@NonNull View itemView, final int colorId) {
            super(itemView);
            mItemView = itemView;
            nameView = itemView.findViewById(R.id.cellMoneyNameView);
            valueView = itemView.findViewById(R.id.cellMoneyValueView);

            final Context context = valueView.getContext();
            valueView.setTextColor(ContextCompat.getColor(context, colorId));

        }
        public void bind(final MoneyCellModel moneyCellModel, final boolean isSelected) {

            mItemView.setSelected(isSelected);
            nameView.setText(moneyCellModel.getName());
            valueView.setText(
                  valueView.getContext().getResources().getString(R.string.price_with_currency, String.valueOf(moneyCellModel.getPrice()))
           );
        }

        public void setListener(ItemsAdapterListener listener, final MoneyCellModel moneyCellModel, final int position) {
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(moneyCellModel, position);
                }
            });

            mItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(moneyCellModel, position);
                    return false;
                }
            });
        }
    }

}
