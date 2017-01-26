package com.udacity.stockhawk.widget;


public class WidgetItem {

    private String  mStockName;
    private double  mStockPrice;
    private double  mStockChange;

    public WidgetItem() {
    }


    public WidgetItem(String mStockName, double mStockPrice, double mStockChange) {
        this.mStockName = mStockName;
        this.mStockPrice = mStockPrice;
        this.mStockChange = mStockChange;

    }

    public String getStockName() {
        return mStockName;
    }

    public void setStockName(String mStockName) {
        this.mStockName = mStockName;
    }

    public double getStockPrice() {
        return mStockPrice;
    }

    public void setStockPrice(float mStockPrice) {
        this.mStockPrice = mStockPrice;
    }

    public double getStockChange() {
        return mStockChange;
    }


    public void setmStockChange(double mStockChange) {
        this.mStockChange = mStockChange;
    }
}
