package com.udacity.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;

public class StockWidgetService extends RemoteViewsService {

    private Context mContext;

    public StockWidgetService() {
    }

    public StockWidgetService(Context context){
        mContext = context;
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockListProvider(getApplicationContext(), intent);
    }

    public ArrayList<WidgetItem> getLastWidgetItems(){

        ArrayList<WidgetItem> quotes = new ArrayList<>();

        Cursor itemsCursor = mContext.getContentResolver().query(
                Contract.Quote.URI,
                Contract.Quote.QUOTE_COLUMNS.toArray(new String[]{}),
                null,
                null,
                null);



        if (itemsCursor.getCount() != 0) {

            itemsCursor.moveToFirst();

            do {

                quotes.add(new WidgetItem(itemsCursor.getString(Contract.Quote.POSITION_SYMBOL)
                                         ,itemsCursor.getDouble(Contract.Quote.POSITION_PRICE)
                                         ,itemsCursor.getDouble(Contract.Quote.POSITION_ABSOLUTE_CHANGE)
                                         ));

            } while (itemsCursor.moveToNext());

        }

        itemsCursor.close();

        return quotes;

    }

}




