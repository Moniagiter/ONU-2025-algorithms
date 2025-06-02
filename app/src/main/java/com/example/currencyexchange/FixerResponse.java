package com.example.currencyexchange;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class FixerResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("timestamp")
    private long timestamp;

    @SerializedName("base")
    private String base;

    @SerializedName("date")
    private String date; // "YYYY-MM-DD"

    @SerializedName("rates")
    private Map<String, Double> rates;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Map<String, Double> getRates() { return rates; }
    public void setRates(Map<String, Double> rates) { this.rates = rates; }
}
