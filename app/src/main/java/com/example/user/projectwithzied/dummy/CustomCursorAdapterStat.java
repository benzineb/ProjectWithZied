package com.example.user.projectwithzied.dummy;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.user.projectwithzied.R;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by User on 25/04/2016.
 */
public class CustomCursorAdapterStat extends CursorAdapter {
    public static String Tag="custom";
    private Time timeFromBase;
    Time currentTime;
    public CustomCursorAdapterStat(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.activity_horaires, parent, false);

        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {



        // here we are setting our data
        // that means, take the data from the cursor and put it in views

        TextView textViewPersonName = (TextView) view.findViewById(R.id.dep);
         textViewPersonName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
         Log.d(Tag, "bindViewcursor: " + cursor.getString(1));
     }

        // String txt=cursor.getString( cursor.getColumnIndex( MyTable.COLUMN_TITLE ) )
      // textViewPersonName.setText("abc");
   //   textViewPersonName.setText(cursor.getString(0));
    //   TextView textViewPersonPIN = (TextView) view.findViewById(R.id.arr);
    //    textViewPersonPIN.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
    }

