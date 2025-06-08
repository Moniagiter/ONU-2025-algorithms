package com.example.currencyexchange;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RateAdapter
        extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    public interface OnRateClickListener {
        void onRateClick(Rate rate);
    }

    private List<Rate> rateList;
    private final OnRateClickListener listener;

    public RateAdapter(List<Rate> rateList, OnRateClickListener listener) {
        this.rateList = rateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rate, parent, false);
        return new RateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RateViewHolder holder, int position) {
        Rate rate = rateList.get(position);
        holder.tvCurrencyValue.setText(
                rate.getCurrencyCode() + ": " + rate.getRateValue()
        );
        holder.tvDate.setText(rate.getDate());

        holder.itemView.setOnClickListener(v -> listener.onRateClick(rate));
    }

    @Override
    public int getItemCount() {
        return rateList == null ? 0 : rateList.size();
    }

    public void updateList(List<Rate> newList) {
        this.rateList = newList;
        notifyDataSetChanged();
    }

    /** ViewHolder — не static, чтобы IDE не ругалась на “modifier static not allowed here” */
    class RateViewHolder extends RecyclerView.ViewHolder {
        final TextView tvCurrencyValue;
        final TextView tvDate;

        RateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrencyValue = itemView.findViewById(R.id.tvCurrencyValue);
            tvDate          = itemView.findViewById(R.id.tvDate);
        }
    }
}
