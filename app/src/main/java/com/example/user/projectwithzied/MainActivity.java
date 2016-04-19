package com.example.user.projectwithzied;

import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private String[] arraySpinner;
    static final int dp=0;
    static final int arr=0;
    Cursor c=null;
public static String GareDepart;
   public static Integer IndexGareArrivee;
    public static Integer IndexGareDepart;
  public static  String headsign;
  public  static String departure="Mahdia";
    public static String Tag="jabeur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button partir = (Button) findViewById(R.id.maintenant);
        Button partirpl = (Button) findViewById(R.id.partirpl);
        this.arraySpinner = new String[]{
                "Mahdia", "Ezzahra", "Borj Arif", "Sidi Massaoud", "Mahdia Z.T."
        };
        final Spinner dep = (Spinner) findViewById(R.id.spinner);
        final Spinner arr = (Spinner) findViewById(R.id.spinnerarr);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        dep.setAdapter(adapter);
        arr.setAdapter(adapter);
        GareDepart = dep.getSelectedItem().toString();
        IndexGareDepart = dep.getSelectedItemPosition();
        IndexGareArrivee = arr.getSelectedItemPosition();




    partir.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dep.getSelectedItem() == arr.getSelectedItem()) {
                Toast.makeText(MainActivity.this, "la station du départ doit être differente de l'arrivée ", Toast.LENGTH_LONG).show();
            } else {

                DataBaseHelper myDbHelper = new DataBaseHelper(MainActivity.this);


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

                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
             //     c = myDbHelper.query("stop_times,stops",new String[]{"departure_time"}, "stop_times.stop_id=stops.stop_id AND stops.stop_name="+departure+" and trip_id<43 AND stop_times.stop_headsign="+headsign+"", null, null, null,"departure_time");
              c = myDbHelper.query("stop_times,stops", null, null, null, null, null, null);
                Log.d(Tag, "query:" + departure);
                if (c.moveToFirst()) {
                    do {
                        Toast.makeText(MainActivity.this,
                                "departure_time=" + c.getString(0), Toast.LENGTH_LONG).show();
                    } while (c.moveToNext());

                }
            }
        }
    });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static String getGareDepart() {
        return GareDepart;
    }
    public static String getHeadsign() {
        if (IndexGareDepart < IndexGareArrivee) {
            headsign = "Sousse";
        } else {
            headsign = "Mahdia";
        }
        return headsign;
    }

}
