package com.example.myapplication;

public class ForecastItem {
    private long dt;
    private Temp temp;
    private double pressure;
    private double speed; // скорость ветра

    public long getDt() {
        return dt;
    }
    public void setDt(long dt) {
        this.dt = dt;
    }
    public Temp getTemp() {
        return temp;
    }
    public void setTemp(Temp temp) {
        this.temp = temp;
    }
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
