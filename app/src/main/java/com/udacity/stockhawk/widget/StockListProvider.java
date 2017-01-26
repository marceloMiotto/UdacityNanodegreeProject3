package com.udacity.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockListProvider implements RemoteViewsService.RemoteViewsFactory{


    private List<WidgetItem> mWidgetItems = new ArrayList<>();
    private Context mContext;

    private final DecimalFormat dollarFormatWithPlus;
    private final DecimalFormat dollarFormat;
    private final DecimalFormat percentageFormat;

    public StockListProvider(Context context, Intent intent) {
        mContext = context;

        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix(mContext.getResources().getString(R.string.dollar_positive_prefix));
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix(mContext.getResources().getString(R.string.positive_prefix));


    }

    public void onCreate() {


        initList();
    }

    @Override
    public void onDataSetChanged() {
        initList();

    }

    private void initList() {


        StockWidgetService stockWidgetService = new StockWidgetService(mContext);
        mWidgetItems = stockWidgetService.getLastWidgetItems();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    @SuppressWarnings("deprecation")
    public RemoteViews getViewAt(int position) {
        // Construct a remote views item based on the app widget item XML file,
        // and set the text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);
        rv.setTextViewText(R.id.symbol, mWidgetItems.get(position).getStockName());
        rv.setTextViewText(R.id.price,dollarFormat.format(mWidgetItems.get(position).getStockPrice()));
        rv.setTextViewText(R.id.change, dollarFormatWithPlus.format(mWidgetItems.get(position).getStockChange()));
        if(mWidgetItems.get(position).getStockChange() < 0){
            rv.setTextColor(R.id.change,mContext.getResources().getColor(R.color.material_red_700));
        }else{
            rv.setTextColor(R.id.change,mContext.getResources().getColor(R.color.material_green_700));
        }
        // Return the remote views object.
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }





}
