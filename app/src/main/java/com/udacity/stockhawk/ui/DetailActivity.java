package com.udacity.stockhawk.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.StockHistory;
import com.udacity.stockhawk.service.StockHistoryService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends Activity {


    @BindView(R.id.chart) LineChart mLineChart;
    private String mSymbol;
    private SimpleDateFormat mDateFormat;
    List<StockHistory> mStockHistories;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mSymbol = getIntent().getStringExtra(getResources().getString(R.string.detail_symbol));

        StockHistoryService stockHistoryService = new StockHistoryService(this,mSymbol);

        mStockHistories = stockHistoryService.getStockHistory();

        List<Entry> entries = new ArrayList<>();

        for (StockHistory stockHistory : mStockHistories) {
            // turn your data into Entry objects
            entries.add(new Entry(stockHistory.getValueX(), stockHistory.getValueY()));
        }

        LineDataSet setComp1 = new LineDataSet(entries, getResources().getString(R.string.graph_label));
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setValueTextColor(Color.WHITE);
        setComp1.setColor(getResources().getColor(R.color.colorPrimary));
        setComp1.setFillAlpha(Color.WHITE);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);


        LineData data = new LineData(dataSets);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return setLabel((int)value);
            }
        };

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setAxisMaximum(13);
        xAxis.setAxisMinimum(1);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);

        YAxis left = mLineChart.getAxisLeft();
        left.setDrawLabels(true);
        left.setDrawAxisLine(true);
        left.setDrawGridLines(true);
        left.setDrawZeroLine(true);
        left.setTextColor(Color.WHITE);
        mLineChart.getAxisRight().setEnabled(false);

        mLineChart.setNoDataText(getResources().getString(R.string.graph_no_values));
        Description description = new Description();
        description.setText("");
        mLineChart.setDescription(description);
        mLineChart.setData(data);
        mLineChart.invalidate();

    }

    public String setLabel(int value){

        int correctLabel = value-1;
        String monthText;
        String label = mStockHistories.get(correctLabel).getLabel();
        int month = Integer.valueOf(label.substring(4,6));

        String year  = label.substring(0,4);

        switch (month){
            case 1: monthText=getResources().getString(R.string.axis_label_jan); break;
            case 2: monthText=getResources().getString(R.string.axis_label_feb); break;
            case 3: monthText=getResources().getString(R.string.axis_label_mar); break;
            case 4: monthText=getResources().getString(R.string.axis_label_apr); break;
            case 5: monthText=getResources().getString(R.string.axis_label_may); break;
            case 6: monthText=getResources().getString(R.string.axis_label_jun); break;
            case 7: monthText=getResources().getString(R.string.axis_label_jul); break;
            case 8: monthText=getResources().getString(R.string.axis_label_aug); break;
            case 9: monthText=getResources().getString(R.string.axis_label_sep); break;
            case 10: monthText=getResources().getString(R.string.axis_label_oct); break;
            case 11: monthText=getResources().getString(R.string.axis_label_nov); break;
            case 12: monthText=getResources().getString(R.string.axis_label_dec); break;
            default: monthText = "";

        }

        return monthText+"/"+year;

    }

}
