package com.example.currencyexchange;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "rates")
public class Rate {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String currencyCode;

    private double rateValue;

    @NonNull
    private String date;

    public Rate(@NonNull String currencyCode, double rateValue, @NonNull String date) {
        this.currencyCode = currencyCode;
        this.rateValue    = rateValue;
        this.date         = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(@NonNull String currencyCode) { this.currencyCode = currencyCode; }

    public double getRateValue() { return rateValue; }
    public void setRateValue(double rateValue) { this.rateValue = rateValue; }

    @NonNull
    public String getDate() { return date; }
    public void setDate(@NonNull String date) { this.date = date; }
}
