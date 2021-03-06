package com.example.user.projectwithzied;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity   {
    private String[] arraySpinner;
    static final int dp=0;
    static final int arr=0;
    Cursor c=null;
   public  Cursor cMap=null;
    public static String GareDepart;
    public static String GareArrivee;
    public static Integer IndexGareArrivee;
    public static Integer IndexGareDepart;
    public static  String headsign;
    public static String Tag="jabeur";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final HashMap<String,List<Double>> temp = new HashMap<String,List<Double>>();
        final List<Double> values = new ArrayList<Double>();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView prochain=(TextView) findViewById(R.id.title) ;
        TextView departure=(TextView) findViewById(R.id.depart) ;
        TextView arriv=(TextView) findViewById(R.id.arrivee) ;
        Button partir = (Button) findViewById(R.id.maintenant);
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Button partirpl = (Button) findViewById(R.id.partirpl);

        Typeface roboto = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Medium.ttf"); //use this.getAssets if you are calling from an Activity
        partirpl.setTypeface(roboto);
        partir.setTypeface(roboto);
        prochain.setTypeface(roboto);
        arriv.setTypeface(roboto);
        departure.setTypeface(roboto);
        this.arraySpinner = new String[]{

                "Mahdia",
                "Ezzahra" ,
                "Borj Arif" ,
                "Sidi Massaoud" ,
                "Mahdia Z.T." ,
                "Baghdadi" ,
                "Békalta",
                "Téboulba" ,
                "Téboulba Z.I." ,
                "Moknine" ,
                "Moknine Gribaa" ,
                "Ksar Hellal" ,
                "Ksar Hellal Z.I.",
                "Sayada" ,
                "Lamta" ,
                "Bouhadjar" ,
                "Ksibet Bennane",
                "Khnis Bembla" ,
                "Frina" ,
                "Monastir Zone Industrielle" ,
                "La Faculté 2",
                "Monastir",
                "La Faculté" ,
                "Aéroport",
                "Les Hôtels",
                "Sahline Sabkha",
                "Sahline Ville" ,
                "Sousse Zone Industrielle",
                "Arrêt Dépot ",
                "Sousse Sud",
                "Sousse Mohamed V",
                "Sousse Bab Jédid"

        };
        final Spinner dep = (Spinner) findViewById(R.id.spinner);
        final Spinner arr = (Spinner) findViewById(R.id.spinnerarr);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        dep.setAdapter(adapter);
        arr.setAdapter(adapter);




    partir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //écouteur bouton vas y

                if (dep.getSelectedItem() == arr.getSelectedItem()) {

                    Toast.makeText(MainActivity.this, "la station du départ doit être differente de l'arrivée ", Toast.LENGTH_LONG).show();
                } else {
                    vibe.vibrate(100);
                    GareDepart = dep.getSelectedItem().toString();
                    GareArrivee = arr.getSelectedItem().toString();
                    IndexGareDepart = dep.getSelectedItemPosition();
                    IndexGareArrivee = arr.getSelectedItemPosition();
                    Intent horaire=new Intent(MainActivity.this,Horaires.class);
                    horaire.putExtra("indiceDep",IndexGareDepart);
                    horaire.putExtra("indicearr",IndexGareArrivee);
                    horaire.putExtra("GareDep",GareDepart);
                    horaire.putExtra("Gareearr",GareArrivee);
                    startActivity(horaire);
                }
            }
        });
        partirpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {

                if (dep.getSelectedItem() == arr.getSelectedItem()) {

                    Toast.makeText(MainActivity.this, "la station du départ doit être differente de l'arrivée ", Toast.LENGTH_LONG).show();
                } else {

                    vibe.vibrate(100);
                    GareDepart = dep.getSelectedItem().toString();
                    GareArrivee = arr.getSelectedItem().toString();
                    IndexGareDepart = dep.getSelectedItemPosition();
                    IndexGareArrivee = arr.getSelectedItemPosition();

                    DataBaseHelper myDbHelper = new DataBaseHelper(MainActivity.this);
                    Horaires horaires=new Horaires();
                    Intent horairest=new Intent(MainActivity.this,HorairesStat.class);
                    startActivity(horairest);
                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

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
Intent menu=new Intent(this,propos.class);
            startActivity(menu);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static String getGareDepart() {
        return GareDepart;
    }

    public static String getGareArrivee() {
        return GareArrivee;
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


