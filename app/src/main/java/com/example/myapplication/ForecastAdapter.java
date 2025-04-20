package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<ListItem> forecastList;

    public ForecastAdapter(List<ListItem> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        ListItem item = forecastList.get(position);
        holder.dateTextView.setText("Дата/Время: " + item.getDt_txt());
        holder.tempTextView.setText("Температура: " + item.getMain().getTemp() + "°C");
        holder.windTextView.setText("Ветер: " + item.getWind().getSpeed() + " м/с");
        holder.pressureTextView.setText("Давление: " + item.getMain().getPressure() + " гПа");
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView tempTextView;
        TextView windTextView;
        TextView pressureTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView     = itemView.findViewById(R.id.dateTextView);
            tempTextView     = itemView.findViewById(R.id.tempTextView);
            windTextView     = itemView.findViewById(R.id.windTextView);
            pressureTextView = itemView.findViewById(R.id.pressureTextView);
        }
    }
}
