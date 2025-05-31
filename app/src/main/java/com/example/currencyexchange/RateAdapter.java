package com.example.currencyexchange;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Простой RecyclerView.Adapter для отображения списка Rate.
 */
public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    private List<Rate> rateList;

    public RateAdapter(List<Rate> rateList) {
        this.rateList = rateList;
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // «Inflate» наш item_rate.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rate, parent, false);
        return new RateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        Rate rate = rateList.get(position);
        // Формируем строку “USD: 1.1875”
        String currencyAndValue = rate.getCurrencyCode() + ": " + rate.getRateValue();
        holder.tvCurrencyValue.setText(currencyAndValue);
        // Формируем дату “Дата: 2023-08-05”
        holder.tvDate.setText("Дата: " + rate.getDate());
    }

    @Override
    public int getItemCount() {
        return rateList.size();
    }

    // ViewHolder для одного элемента списка
    static class RateViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrencyValue;
        TextView tvDate;

        public RateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrencyValue = itemView.findViewById(R.id.tvCurrencyValue);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    // В случае, если понадобилось обновить список извне
    public void updateList(List<Rate> newList) {
        this.rateList = newList;
        notifyDataSetChanged();
    }
}
