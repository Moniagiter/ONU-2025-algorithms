package com.example.myapplication;

import java.util.List;

public class ForecastResponse {
    private String cod;
    private int cnt;
    private List<ListItem> list;
    private City city;

    // геттеры/сеттеры
    public String getCod() {
        return cod;
    }
    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getCnt() {
        return cnt;
    }
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<ListItem> getList() {
        return list;
    }
    public void setList(List<ListItem> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
}
