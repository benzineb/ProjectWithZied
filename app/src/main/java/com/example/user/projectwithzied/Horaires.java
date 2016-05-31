package com.example.user.projectwithzied;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.projectwithzied.dummy.CustomCursorAdapter;

import java.io.IOException;
import java.sql.SQLException;

public class Horaires extends HorairesStat {
    private CustomCursorAdapter customAdapter;
    private ListView listView;
    private final String Tag = "jab";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_horaires);
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        DataBaseHelper myDbHelper = new DataBaseHelper(Horaires.this);
        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {

            try {
                throw sqle;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(Horaires.this, "success", Toast.LENGTH_SHORT).show();
        listView = (ListView) findViewById(R.id.list_data);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        final MainActivity mainActivity = new MainActivity();
        String depart = mainActivity.getGareDepart();
        String head = mainActivity.getHeadsign();

        final Cursor cur = db.rawQuery("select  _id,arrival_time from stop_times,stops  where stop_times.stop_id=stops._id  and trip_id>43 and stops.stop_name=? and stop_times.stop_headsign=? order by departure_time", new String[]{depart, head});

        Log.d(Tag, "horaires:");
        if (cur.moveToFirst()) {
            do {
                Log.d(Tag, "onClick:" + cur.getString(1));
            } while (cur.moveToNext());

        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isOnline() == false) {
                    Toast.makeText(Horaires.this, "Vérifier connexion internet", Toast.LENGTH_LONG).show();
                } else  {
                    vibe.vibrate(100);
                    Log.d(Tag, "clicked on item: " + position);
                    Intent map = new Intent(Horaires.this, MapsActivity.class);
                    String selected = ((TextView) view.findViewById(R.id.dep)).getText().toString();
                    map.putExtra("tempDeDepart", selected);
                    startActivity(map);
                }
            }
        });

        // Database query can be a time consuming task ..
        // so its safe to call database query in another thread
        // Handler, will handle this stuff for you <img src="http://s0.wp.com/wp-includes/images/smilies/icon_smile.gif?m=1129645325g" alt=":)" class="wp-smiley">

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                customAdapter = new CustomCursorAdapter(Horaires.this, cur);
                listView.setAdapter(customAdapter);
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Horaires.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public boolean isEnabled(int position) {
        return false;
    }
}