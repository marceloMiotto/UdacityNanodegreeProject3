package com.udacity.stockhawk.service;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.StockHistory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TreeMap;

public class StockHistoryService {

    private Context mContext;
    private Uri mPath;
    private String mSymbol;
    String[] mLines;
    private final DecimalFormat dollarFormat;
    private SimpleDateFormat mDateFormat;



    public StockHistoryService(Context context, String symbol){

        this.mContext = context;
        this.mSymbol = symbol;
        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    }

    public ArrayList<StockHistory> getStockHistory(){

        ArrayList<StockHistory> quotes = new ArrayList<>();
        mPath = Contract.Quote.makeUriForStock(mSymbol);

        Cursor itemsCursor = mContext.getContentResolver().query(
                mPath,
                Contract.Quote.QUOTE_COLUMNS.toArray(new String[]{}),
                null,
                null,
                null);




        if (itemsCursor.getCount() != 0) {
            itemsCursor.moveToFirst();

            do {
                mLines = itemsCursor.getString(Contract.Quote.POSITION_HISTORY).split("\\n");
            } while (itemsCursor.moveToNext());


        }


        itemsCursor.close();


        TreeMap<String,Float> allValues = new TreeMap<>();

        for(String s: mLines){
            mDateFormat = new SimpleDateFormat(mContext.getResources().getString(R.string.stock_history_simple_date_format));
            allValues.put(mDateFormat.format(new Date(Long.valueOf(s.substring(0, s.indexOf(","))))),Float.valueOf(s.substring(s.indexOf(",") + 2)));
        }


        TreeMap<String, Float> valuesToPlot = new TreeMap<>();


        for(String key: allValues.keySet()){
           valuesToPlot.put(key.substring(0,6),allValues.get(key));
        }

        float i = 1;


        for(String key: valuesToPlot.keySet()){
            quotes.add(new StockHistory(i++,valuesToPlot.get(key),key));
        }

        return quotes;

    }

}
