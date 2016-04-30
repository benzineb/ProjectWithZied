package com.example.user.projectwithzied;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = "mapp";
    public String station;
    Cursor mp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng gare = new LatLng(35.5008333078298, 11.0644082609385);
        LatLng Mahdia = new LatLng(35.5008333078298, 11.0644082609385);
        LatLng BorjArif=new LatLng(lat("Borj Arif"),lon("Borj Arif"));
       LatLng SM=new LatLng(lat("Sidi Massaoud"),lon("Sidi Massaoud"));
        LatLng MZT=new LatLng(lat("Mahdia Z.T."),lon("Mahdia Z.T."));
        LatLng BAGH=new LatLng(lat("Baghdadi"),lon("Baghdadi"));
        LatLng BKLT=new LatLng(lat( "Békalta"),lon( "Békalta"));
        LatLng TBL=new LatLng(lat("Téboulba"),lon("Téboulba"));
        LatLng TBLZI=new LatLng(lat( "Téboulba Z.I."),lon( "Téboulba Z.I."));
        LatLng MKN=new LatLng(lat("Moknine"),lon("Moknine"));
        LatLng MKNG=new LatLng(lat( "Moknine Gribaa"),lon( "Moknine Gribaa"));
        LatLng KH=new LatLng(lat(  "Ksar Hellal"),lon(  "Ksar Hellal"));
        LatLng KHZI=new LatLng(lat("Ksar Hellal Z.I."),lon("Ksar Hellal Z.I."));
        LatLng SAYADA=new LatLng(lat( "Sayada"),lon( "Sayada"));
        LatLng LAMTA=new LatLng(lat( "Lamta"),lon( "Lamta"));
        LatLng BOUHDJAR=new LatLng(lat( "Bouhadjar"),lon( "Bouhadjar"));
        LatLng KSIBA=new LatLng(lat( "Ksibet Bennane"),lon( "Ksibet Bennane"));
        LatLng KHNIS=new LatLng(lat( "Khnis Bembla"),lon( "Khnis Bembla"));
        LatLng FRINA=new LatLng(lat( "Frina" ),lon( "Frina" ));
        LatLng MONASTIRZI=new LatLng(lat("Monastir Zone Industrielle"),lon("Monastir Zone Industrielle"));
        LatLng FAC2=new LatLng(lat(  "La Faculté 2" ),lon(  "La Faculté 2" ));
        LatLng MONASTIR=new LatLng(lat( "Monastir" ),lon( "Monastir" ));
        LatLng FAC=new LatLng(lat( "La Faculté" ),lon(  "La Faculté" ));
        LatLng AEROPORT=new LatLng(lat(  "Aéroport"),lon(  "Aéroport" ));
        LatLng HOTELS=new LatLng(lat(  "Les Hôtels"),lon(  "Les Hôtels" ));
        LatLng SAHLINESABKHA=new LatLng(lat(    "Sahline Sabkha" ),lon(   "Sahline Sabkha" ));
        LatLng SAHLINEVILLE=new LatLng(lat(   "Sahline Ville" ),lon(   "Sahline Ville" ));
        LatLng SOUSSEZI=new LatLng(lat(  "Sousse Zone Industrielle" ),lon(  "Sousse Zone Industrielle" ));
        LatLng DEPOT=new LatLng(lat( "Arrêt Dépot" ),lon("Arrêt Dépot" ));
        LatLng SOUSSESUD=new LatLng(lat(  "Sousse Sud" ),lon(  "Sousse Sud" ));
        LatLng MED5=new LatLng(lat( "Sousse Mohamed V" ),lon( "Sousse Mohamed V" ));
        LatLng BEBJDID=new LatLng(lat("Sousse Bab Jédid" ),lon("Sousse Bab Jédid" ));


      /*  try {
            Log.d(TAG, "onMapReady: "+coordonnee(x));
            Toast.makeText(MapsActivity.this,"longitude de mahdia"+coordonnee(x),Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(Mahdia).title("Marker in Mahdia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Mahdia));
        mMap.addMarker(new MarkerOptions()
                .title("La Gare")
                .snippet("The most wonderful.")
                .position(Mahdia));
        mMap.addMarker(new MarkerOptions()
                .title("EZZAHRA")
                .snippet("The most wonderful.")
                .position(new LatLng(35.5000434191629, 11.0483183619427)));
        mMap.addMarker(new MarkerOptions()
                .title("borj arif")
                .snippet("The most wonderful.")
                .position(new LatLng(35.5061928462048, 11.0303670358646)));
        mMap.addMarker(new MarkerOptions()
                .title("sidi messaoud")
                .snippet("The most wonderful.")
                .position(new LatLng(35.5210715684533, 11.02721426692)));
        mMap.addMarker(new MarkerOptions()
                .title("Mahdia Zone Touristique")
                .snippet("The most wonderful.")
                .position(MZT));
        mMap.addMarker(new MarkerOptions()
                .title("BAGHDADI")
                .snippet("The most wonderful.")
                .position(BAGH));
        mMap.addMarker(new MarkerOptions()
                .title("Bekalta")
                .snippet("The most wonderful.")
                .position(BKLT));
        mMap.addMarker(new MarkerOptions()
                .title("Teboulba")
                .snippet("The most wonderful.")
                .position(TBL));
        mMap.addMarker(new MarkerOptions()
                .title("Teboulba Zone Indistruelle")
                .snippet("The most wonderful.")
                .position(TBLZI));
        mMap.addMarker(new MarkerOptions()
                .title("Moknine")
                .snippet("The most wonderful.")
                .position(MKN));
        mMap.addMarker(new MarkerOptions()
                .title("Moknine Gribaa")
                .snippet("The most wonderful.")
                .position(MKNG));
        mMap.addMarker(new MarkerOptions()
                .title("Ksar Helal")
                .snippet("The most wonderful.")
                .position(KH));
        mMap.addMarker(new MarkerOptions()
                .title("Ksar Helal Zone Indistruelle")
                .snippet("The most wonderful.")
                .position(KHZI));
        mMap.addMarker(new MarkerOptions()
                .title("Sayyada")
                .snippet("The most wonderful.")
                .position(SAYADA));
        mMap.addMarker(new MarkerOptions()
                .title("Lamta")
                .snippet("The most wonderful.")
                .position(LAMTA));
        mMap.addMarker(new MarkerOptions()
                .title("Bouhdjar")
                .snippet("The most wonderful.")
                .position(BOUHDJAR));
        mMap.addMarker(new MarkerOptions()
                .title("Ksiba")
                .snippet("The most wonderful.")
                .position(KSIBA));
        mMap.addMarker(new MarkerOptions()
                .title("Khnis")
                .snippet("The most wonderful.")
                .position(KHNIS));
        mMap.addMarker(new MarkerOptions()
                .title("Frina")
                .snippet("The most wonderful.")
                .position(FRINA));
        mMap.addMarker(new MarkerOptions()
                .title("Monastir Zone Indistruelle")
                .snippet("The most wonderful.")
                .position(MONASTIRZI));
        mMap.addMarker(new MarkerOptions()
                .title("Faculté 2")
                .snippet("The most wonderful.")
                .position(FAC2));
        mMap.addMarker(new MarkerOptions()
                .title("Monastir")
                .snippet("The most wonderful.")
                .position(MONASTIR));
        mMap.addMarker(new MarkerOptions()
                .title("Faculté")
                .snippet("The most wonderful.")
                .position(FAC));
        mMap.addMarker(new MarkerOptions()
                .title("Aeroport")
                .snippet("The most wonderful.")
                .position(AEROPORT));
        mMap.addMarker(new MarkerOptions()
                .title("Les Hotels")
                .snippet("The most wonderful.")
                .position(HOTELS));
        mMap.addMarker(new MarkerOptions()
                .title("Sahline Ville")
                .snippet("The most wonderful.")
                .position(SAHLINESABKHA));
        mMap.addMarker(new MarkerOptions()
                .title("Sahline Ville")
                .snippet("The most wonderful.")
                .position(SAHLINEVILLE));
        mMap.addMarker(new MarkerOptions()
                .title("Sousse Zone Industrielle")
                .snippet("The most wonderful.")
                .position(SOUSSEZI));
        mMap.addMarker(new MarkerOptions()
                .title("Depot")
                .snippet("The most wonderful.")
                .position(DEPOT));
        mMap.addMarker(new MarkerOptions()
                .title("Sousse Sud")
                .snippet("The most wonderful.")
                .position(SOUSSESUD));
        mMap.addMarker(new MarkerOptions()
                .title("Med V")
                .snippet("The most wonderful.")
                .position(MED5));
        mMap.addMarker(new MarkerOptions()
                .title("Beb Jdid")
                .snippet("The most wonderful.")
                .position(BEBJDID));
        MainActivity mainActivity=new MainActivity();
    /*    mMap.addMarker(new MarkerOptions()
                .title("You are here")
                .snippet("The most wonderful.")
                .position(new LatLng(mainActivity.lon,mainActivity.lat)));
      //  Log.d(TAG, "onMapReady: "+mainActivity.getLat()));*/









        // Polylines are useful for marking paths and routes on the map.
        mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(35.5008333078298, 11.0644082609385))  // La gare
                .add(new LatLng(35.496206, 11.059541))
                .add(new LatLng(35.5000434191629, 11.0483183619427))  // EZZAHRA
                .add(new LatLng(35.5061928462048, 11.0303670358646))  // borj arif
                .add(new LatLng(35.5210715684533, 11.02721426692))  // sidi massaoud
                .add(MZT)  // Mahdia ZT

                .add(BAGH)  // Baghdadi
                .add(BKLT)  // Bekalta
                .add(TBL)  // Teboulba
                .add(TBLZI)//Teboulba Z.I
                .add(MKN)//Moknine
                .add(MKNG)//Moknine Gribaa
                .add(KH)//K.Helal
                .add(KHZI)
                .add(SAYADA)//Sayyada
                .add(LAMTA)
                .add(BOUHDJAR)//Bouhdjar
                .add(KSIBA)//Ksiba
                .add(KHNIS)//khnis
                .add(FRINA)//Frina
                .add(MONASTIRZI)//Monastir Z.I
                .add(FAC2)//La Facult�
                .add(MONASTIR)//La gare El Monastir
                .add(FAC)//La Facult�

                .add(new LatLng(35.752273, 10.794700))//foret
                .add(new LatLng(35.757845, 10.787834))//foret
                .add(new LatLng(35.769546, 10.787491))//foret
                .add(new LatLng(35.772192, 10.781654))//foret
                .add(AEROPORT)//L'a�roport
                .add(HOTELS)//Les hotels
                .add(SAHLINESABKHA)//Sahline Sabkha
                .add(SAHLINEVILLE)
                .add(SOUSSEZI)//Sousse Z.I
                .add(DEPOT)//Depot
                .add(SOUSSESUD)//Sousse Sud
                .add(MED5)//Med V
                .add(BEBJDID)//beb Jdid

        );

    }


    public Double lat(String station)  {
        Double  lat = null;
        DataBaseHelper myDbHelper = new DataBaseHelper(MapsActivity.this);
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

        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        final Cursor cur = db.rawQuery(" select stop_lat,stop_lon from stops where stop_name=?", new String[]{station});

        Log.d(TAG, "done:");
        if (cur.moveToFirst()) {
            do {
               lat= cur.getDouble(0);
            } while (cur.moveToNext());

        }
        return lat;
    }
    public Double lon(String station)  {
        Double lon = null;
        DataBaseHelper myDbHelper = new DataBaseHelper(MapsActivity.this);
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

        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        final Cursor cur = db.rawQuery(" select stop_lat,stop_lon from stops where stop_name=?", new String[]{station});

        Log.d(TAG, "done:");
        if (cur.moveToFirst()) {
            do {
               lon= cur.getDouble(1);
            } while (cur.moveToNext());

        }
        return lon;
    };

    public String getStation() {
        return station;
    }

    public GoogleMap getmMap() {
        return mMap;
    }
}
