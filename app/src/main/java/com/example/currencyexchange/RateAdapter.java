package com.example.currencyexchange;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RateAdapter — RecyclerView.Adapter для отображения списка Rate.
 */
public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    private List<Rate> rateList;

    public RateAdapter(List<Rate> rateList) {
        this.rateList = rateList;
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rate, parent, false);
        return new RateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        Rate rate = rateList.get(position);
        String displayCurrency = rate.getCurrencyCode() + ": " + rate.getRateValue();
        holder.tvCurrencyValue.setText(displayCurrency);
        holder.tvDate.setText("Дата: " + rate.getDate());
    }

    @Override
    public int getItemCount() {
        return rateList == null ? 0 : rateList.size();
    }

    /** Позволяет обновить весь список извне и перерисовать RecyclerView */
    public void updateList(List<Rate> newList) {
        this.rateList = newList;
        notifyDataSetChanged();
    }

    /** ViewHolder для одного элемента списка */
    static class RateViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrencyValue;
        TextView tvDate;

        public RateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrencyValue = itemView.findViewById(R.id.tvCurrencyValue);
            tvDate          = itemView.findViewById(R.id.tvDate);
        }
    }
}
