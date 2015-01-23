package com.madsvyat.dynamiclisttest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by N1 on 23.01.2015.
 */
public class ItemsCursorAdapter extends CursorAdapter {


    public ItemsCursorAdapter(Context context) {
        super(context, null, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(DBData.Items.NAME));
        
        TextView titleTextView = (TextView) view.findViewById(R.id.item_title_textView);
        titleTextView.setText(title);
    }
}
