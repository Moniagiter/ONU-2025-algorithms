package com.example.myapplication;

import java.util.List;

public class ListItem {
    private long dt;
    private MainData main;
    private List<WeatherData> weather;
    private Clouds clouds;
    private Wind wind;
    private String dt_txt; // "2021-04-13 12:00:00" — строка с датой/временем

    // геттеры/сеттеры
    public long getDt() {
        return dt;
    }
    public void setDt(long dt) {
        this.dt = dt;
    }

    public MainData getMain() {
        return main;
    }
    public void setMain(MainData main) {
        this.main = main;
    }

    public List<WeatherData> getWeather() {
        return weather;
    }
    public void setWeather(List<WeatherData> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDt_txt() {
        return dt_txt;
    }
    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
