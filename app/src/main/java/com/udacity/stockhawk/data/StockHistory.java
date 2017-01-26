package com.udacity.stockhawk.data;


public class StockHistory {

    private float valueX;
    private float valueY;
    private String label;

    public StockHistory() {
    }

    public StockHistory(float valueX, float valueY, String label) {
        this.valueX = valueX;
        this.valueY = valueY;
        this.label = label;
    }

    public float getValueX() {
        return valueX;
    }

    public void setValueX(float valueX) {
        this.valueX = valueX;
    }

    public float getValueY() {
        return valueY;
    }

    public void setValueY(float valueY) {
        this.valueY = valueY;
    }

    public String getLabel(){ return label;}

    public void setLabel(String label){this.label = label;}



}
