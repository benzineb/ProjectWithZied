package com.example.user.projectwithzied;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private String[] arraySpinner;
    static final int dp=0;
    static final int arr=0;
    Cursor c=null;
   public  Cursor cMap=null;
    public static String GareDepart;
    public static Integer IndexGareArrivee;
    public static Integer IndexGareDepart;
    public static  String headsign;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    // Constant for identifying the dialog
    private static final int DIALOG_ALERT = 10;

    public static String Tag="jabeur";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final HashMap<String,List<Double>> temp = new HashMap<String,List<Double>>();
        final List<Double> values = new ArrayList<Double>();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Log.d(Tag, "onCreate:client créé");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button partir = (Button) findViewById(R.id.maintenant);
        Button partirpl = (Button) findViewById(R.id.partirpl);
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
        public void onClick(View v) {

            if (dep.getSelectedItem() == arr.getSelectedItem()) {

                Toast.makeText(MainActivity.this, "la station du départ doit être differente de l'arrivée ", Toast.LENGTH_LONG).show();
            } else {
                GareDepart = dep.getSelectedItem().toString();
                IndexGareDepart = dep.getSelectedItemPosition();
                IndexGareArrivee = arr.getSelectedItemPosition();
                DataBaseHelper myDbHelper = new DataBaseHelper(MainActivity.this);

            Horaires horaires=new Horaires();

           /*     try {

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

                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();*/
           //     cMap=myDbHelper.map("stops",null, null, null, null, null, null);
               Intent horaire=new Intent(MainActivity.this,Horaires.class);

               startActivity(horaire);



              /*  AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.list_view_horaires, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("List");
                ListView lv = (ListView) convertView.findViewById(R.id.list_data);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_2,c.getString(0));
                lv.setAdapter(adapter);
                alertDialog.setView(convertView);
                alertDialog.show();*/


    /*           if (cMap.moveToFirst()) {
                    do {
                       values.add(cMap.getDouble(1));
                        values.add(cMap.getDouble(2));
                      temp.put(cMap.getString(0),values);

                       Toast.makeText(MainActivity.this,
                                "name"+cMap.getString(0)+"\n"+
                                "longitude=" + cMap.getDouble(1)+
                                        "largitude"+cMap.getString(2)
                                , Toast.LENGTH_LONG).show();
                       Log.d(Tag, "cMap:"+c.getString(0));
                    } while (cMap.moveToNext());

                }
                printMap(temp);
                Log.d(Tag, "hashmap: ");*/
       /*     c = myDbHelper.query("stop_times,stops", null, null, null, null, null, null);
                Log.d(Tag, "query:" + GareDepart);
                if (c.moveToFirst()) {
                    do {
                        ArrayList list = new ArrayList();
                        Toast.makeText(MainActivity.this,
                                "departure_time=" + c.getString(0), Toast.LENGTH_LONG).show();
                        Log.d(Tag, "onClick:"+c.getString(0));
                    } while (c.moveToNext());

                }*/
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
    @Override
    public void onLocationChanged(Location location) {
        double    lon = location.getLongitude();
        double latt = location.getLatitude();
        Log.d(Tag, "onLocationChanged: ");
        Toast.makeText(this, "longitude" + lon + "lattitude" + latt, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        Log.d(Tag, "request:done");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Toast.makeText(this, "client connecté", Toast.LENGTH_SHORT).show();
    }
    @Override

    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
    public void printMap(HashMap mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
           HashMap.Entry pair = (HashMap.Entry)it.next();
        Toast.makeText(MainActivity.this,"key= "+pair.getKey() + " values= " + pair.getValue(),Toast.LENGTH_LONG).show();

            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}


