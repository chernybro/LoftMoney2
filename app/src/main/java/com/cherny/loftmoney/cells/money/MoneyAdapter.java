package com.cherny.loftmoney.cells.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cherny.loftmoney.R;

import java.util.ArrayList;
import java.util.List;



public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder> {

    private List<MoneyCellModel> moneyCellModels = new ArrayList<>();
    private MoneyAdapterClick moneyAdapterClick;

    private final int colorId;

    public MoneyAdapter(final int colorId) {
        this.colorId = colorId;
    }



    public void setMoneyAdapterClick(MoneyAdapterClick moneyAdapterClick) {
        this.moneyAdapterClick = moneyAdapterClick;
    }

    public void setData(List<MoneyCellModel> moneyCellModels) {
        this.moneyCellModels.clear();
        this.moneyCellModels.addAll(moneyCellModels);
        notifyDataSetChanged();
    }
    public void addData(List<MoneyCellModel> moneyCellModels) {
        this.moneyCellModels.addAll(moneyCellModels);
        notifyDataSetChanged();
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
        //return new MoneyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_money, parent, false));
    }

    /*public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, viewGroup, false);
        return new ItemViewHolder(view);
    }*/

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(moneyCellModels.get(position));
    }

    @Override
    public int getItemCount() {
        return moneyCellModels.size();
    }

    static class MoneyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView valueView;
        //MoneyAdapterClick moneyAdapterClick;

        public MoneyViewHolder(@NonNull View itemView, final int colorId) {
            super(itemView);

            nameView = itemView.findViewById(R.id.cellMoneyNameView);
            valueView = itemView.findViewById(R.id.cellMoneyValueView);

            final Context context = valueView.getContext();
            valueView.setTextColor(ContextCompat.getColor(context, colorId));

        }
        public void bind(final MoneyCellModel moneyCellModel) {
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (moneyAdapterClick != null) {
                        moneyAdapterClick.onMoneyClick(moneyCellModel);
                    }
                }
            });*/


            nameView.setText(moneyCellModel.getName());
           // valueView.setText(moneyCellModel.getPrice());
            valueView.setText(
                  valueView.getContext().getResources().getString(R.string.price_with_currency, String.valueOf(moneyCellModel.getPrice()))
           );
            //valueView.setTextColor(ContextCompat.getColor(valueView.getContext(), moneyCellModel.getColor()));


        }
    }

}
