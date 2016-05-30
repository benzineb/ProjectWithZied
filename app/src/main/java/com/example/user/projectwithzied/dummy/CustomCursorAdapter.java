package com.example.user.projectwithzied.dummy;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.projectwithzied.R;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by User on 25/04/2016.
 */
public class CustomCursorAdapter extends CustomCursorAdapterStat {
    public static String Tag="custom";

    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }



    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        java.sql.Time timeValueFromCuror = null;
        java.sql.Time timeValueNow = null;
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        String timeStamp = formatter.format(Calendar.getInstance().getTime());
        Log.d(Tag, "bindView: "+timeStamp);
        try {
             timeValueNow = new java.sql.Time(formatter.parse(timeStamp).getTime());

            Log.d(Tag, "bindView2: "+timeValueNow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
             timeValueFromCuror = new java.sql.Time(formatter.parse( cursor.getString(1)).getTime());
            Log.d(Tag, "tmpconv: "+timeValueFromCuror);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // here we are setting our data
        // that means, take the data from the cursor and put it in views

        TextView textViewPersonName = (TextView) view.findViewById(R.id.dep);
     if(timeValueFromCuror.after(timeValueNow)) {
         textViewPersonName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
         textViewPersonName.setTextColor(0xff9933cc);
         Log.d(Tag, "bindViewcursor: " + cursor.getString(1));

     }else
     {
         textViewPersonName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1)))+"    "+"Parti");
         textViewPersonName.setTextColor(0xffcc0000);


     }

    }
}
