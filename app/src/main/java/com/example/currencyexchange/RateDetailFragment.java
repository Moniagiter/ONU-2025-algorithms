package com.example.currencyexchange;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RateDetailFragment extends Fragment {
    private static final String ARG_CODE  = "arg_code";
    private static final String ARG_VALUE = "arg_value";
    private static final String ARG_DATE  = "arg_date";

    public static RateDetailFragment newInstance(String code, double value, String date) {
        Bundle args = new Bundle();
        args.putString(ARG_CODE, code);
        args.putDouble(ARG_VALUE, value);
        args.putString(ARG_DATE, date);
        RateDetailFragment f = new RateDetailFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rate_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String code  = getArguments().getString(ARG_CODE);
        double value = getArguments().getDouble(ARG_VALUE);
        String date  = getArguments().getString(ARG_DATE);

        ((TextView)view.findViewById(R.id.tvCode)).setText("Валюта: " + code);
        ((TextView)view.findViewById(R.id.tvValue)).setText("Курс: " + value);
        ((TextView)view.findViewById(R.id.tvDate)).setText("Дата: " + date);
    }
}
