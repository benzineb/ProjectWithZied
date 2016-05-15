package com.example.user.projectwithzied;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;
import static android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static com.example.user.projectwithzied.R.drawable.abc_scrubber_primary_mtrl_alpha;
import static com.example.user.projectwithzied.R.drawable.ic_maps_directions_transit;
import static com.example.user.projectwithzied.R.drawable.ic_maps_directions_walk;
import static com.example.user.projectwithzied.R.drawable.ic_metro_512;

public class MapsActivity extends FragmentActivity  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private String TAG = "mapp";
    public String station;
     public Double attr1,attr2;
    private CameraPosition cp;
    private TextView mTextView;
    public Double lon,lat;
    private long heures,minutes;

    private Marker markerTrain,markerGare,markerEzzahra,markerBorjArif,marker;
    private Marker markerSidiMessaoud,markerBaghdadi,markerMahdiaZT,markerTeboulba,markerTeboulbaZI,markerBekalta,markerMonkine,markerMoknineGribaa,markerKsarHelal,markerKsarHelalZI,markerSayyada,markerLamta,
            markerBouhdjar,markerKsiba,markerKhnis,markerFrina,markerMonastirZI,markerFac2,markerMonastir,markerFac,markerAeroport,markerHotels,markerSahlineSabkha,markerSahlineVille,markerSousseZI,markerDepot,markerSousseSud,markerMed5,markerBebJdid;
public String minName;

    private TextView mTextViewTime;

    private Switch mToggle;
    private String PREFS_NAME;

    protected int getLayoutId() {
        return R.layout.maps_layout;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mTextView = (TextView) findViewById(R.id.station);
        mTextViewTime = (TextView) findViewById(R.id.temp);
        mToggle=(Switch) findViewById(R.id.tgl);

        if (savedInstanceState == null) {
            // First incarnation of this activity.
            mapFragment.setRetainInstance(true);
        } else {
            // Reincarnated activity. The obtained map is the same map instance in the previous
            // activity life cycle. There is no need to reinitialize it.
            mMap = mapFragment.getMap();
        }
        setUpMapIfNeeded();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Log.d(TAG, "onCreate:client créé");

savePreferences();
}
    public void savePreferences() {
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mToggle.setChecked(settings.getBoolean("auto", false));
        mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("auto", mToggle.isChecked());
                editor.commit();

            }
        });

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

        try {
            parseJSON();

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LatLng gare = new LatLng(35.5008333078298, 11.0644082609385);
        LatLng Mahdia = new LatLng(35.5008333078298, 11.0644082609385);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((Mahdia), 10));
        LatLng BorjArif = new LatLng(lat("Borj Arif"), lon("Borj Arif"));
        LatLng SM = new LatLng(lat("Sidi Massaoud"), lon("Sidi Massaoud"));
        LatLng MZT = new LatLng(lat("Mahdia Z.T."), lon("Mahdia Z.T."));
        LatLng BAGH = new LatLng(lat("Baghdadi"), lon("Baghdadi"));
        LatLng BKLT = new LatLng(lat("Békalta"), lon("Békalta"));
        LatLng TBL = new LatLng(lat("Téboulba"), lon("Téboulba"));
        LatLng TBLZI = new LatLng(lat("Téboulba Z.I."), lon("Téboulba Z.I."));
        LatLng MKN = new LatLng(lat("Moknine"), lon("Moknine"));
        LatLng MKNG = new LatLng(lat("Moknine Gribaa"), lon("Moknine Gribaa"));
        LatLng KH = new LatLng(lat("Ksar Hellal"), lon("Ksar Hellal"));
        LatLng KHZI = new LatLng(lat("Ksar Hellal Z.I."), lon("Ksar Hellal Z.I."));
        LatLng SAYADA = new LatLng(lat("Sayada"), lon("Sayada"));
        LatLng LAMTA = new LatLng(lat("Lamta"), lon("Lamta"));
        LatLng BOUHDJAR = new LatLng(lat("Bouhadjar"), lon("Bouhadjar"));
        LatLng KSIBA = new LatLng(lat("Ksibet Bennane"), lon("Ksibet Bennane"));
        LatLng KHNIS = new LatLng(lat("Khnis Bembla"), lon("Khnis Bembla"));
        LatLng FRINA = new LatLng(lat("Frina"), lon("Frina"));
        LatLng MONASTIRZI = new LatLng(lat("Monastir Zone Industrielle"), lon("Monastir Zone Industrielle"));
        LatLng FAC2 = new LatLng(lat("La Faculté 2"), lon("La Faculté 2"));
        LatLng MONASTIR = new LatLng(lat("Monastir"), lon("Monastir"));
        LatLng FAC = new LatLng(lat("La Faculté"), lon("La Faculté"));
        LatLng AEROPORT = new LatLng(lat("Aéroport"), lon("Aéroport"));
        LatLng HOTELS = new LatLng(lat("Les Hôtels"), lon("Les Hôtels"));
        LatLng SAHLINESABKHA = new LatLng(lat("Sahline Sabkha"), lon("Sahline Sabkha"));
        LatLng SAHLINEVILLE = new LatLng(lat("Sahline Ville"), lon("Sahline Ville"));
        LatLng SOUSSEZI = new LatLng(lat("Sousse Zone Industrielle"), lon("Sousse Zone Industrielle"));
        LatLng DEPOT = new LatLng(lat("Arrêt Dépot"), lon("Arrêt Dépot"));
        LatLng SOUSSESUD = new LatLng(lat("Sousse Sud"), lon("Sousse Sud"));
        LatLng MED5 = new LatLng(lat("Sousse Mohamed V"), lon("Sousse Mohamed V"));
        LatLng BEBJDID = new LatLng(lat("Sousse Bab Jédid"), lon("Sousse Bab Jédid"));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(Mahdia));


        markerGare = mMap.addMarker(new MarkerOptions()
                .title("Mahdia")
                .snippet("The most wonderful.")
                .position(gare));
        markerGare.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_metro_512));

//markerGare.setVisible(false);

        markerEzzahra = mMap.addMarker(new MarkerOptions()
                .title("EZZAHRA")
                .snippet("The most wonderful.")
                .position(new LatLng(35.5000434191629, 11.0483183619427)));
        markerBorjArif = mMap.addMarker(new MarkerOptions()
                .title("borj arif")
                .snippet("The most wonderful.")
                .position(new LatLng(35.5061928462048, 11.0303670358646)));
        markerSidiMessaoud = mMap.addMarker(new MarkerOptions()
                .title("sidi messaoud")
                .snippet("The most wonderful.")
                .position(new LatLng(35.5210715684533, 11.02721426692)));
        markerMahdiaZT = mMap.addMarker(new MarkerOptions()
                .title("Mahdia Zone Touristique")
                .snippet("The most wonderful.")
                .position(MZT));
        markerBaghdadi = mMap.addMarker(new MarkerOptions()
                .title("BAGHDADI")
                .snippet("The most wonderful.")
                .position(BAGH));
        markerBekalta = mMap.addMarker(new MarkerOptions()
                .title("Bekalta")
                .snippet("The most wonderful.")
                .position(BKLT));
        markerTeboulba = mMap.addMarker(new MarkerOptions()
                .title("Teboulba")
                .snippet("The most wonderful.")
                .position(TBL));
        markerTeboulbaZI = mMap.addMarker(new MarkerOptions()
                .title("Teboulba Zone Indistruelle")
                .snippet("The most wonderful.")
                .position(TBLZI));
        markerMonkine = mMap.addMarker(new MarkerOptions()
                .title("Moknine")
                .snippet("The most wonderful.")
                .position(MKN));
        markerMoknineGribaa = mMap.addMarker(new MarkerOptions()
                .title("Moknine Gribaa")
                .snippet("The most wonderful.")
                .position(MKNG));
        markerKsarHelal = mMap.addMarker(new MarkerOptions()
                .title("Ksar Helal")
                .snippet("The most wonderful.")
                .position(KH));
        markerKsarHelalZI = mMap.addMarker(new MarkerOptions()
                .title("Ksar Helal Zone Indistruelle")
                .snippet("The most wonderful.")
                .position(KHZI));
        markerSayyada = mMap.addMarker(new MarkerOptions()
                .title("Sayyada")
                .snippet("The most wonderful.")
                .position(SAYADA));
        markerLamta = mMap.addMarker(new MarkerOptions()
                .title("Lamta")
                .snippet("The most wonderful.")
                .position(LAMTA));
        markerBouhdjar = mMap.addMarker(new MarkerOptions()
                .title("Bouhdjar")
                .snippet("The most wonderful.")
                .position(BOUHDJAR));
        markerKsiba = mMap.addMarker(new MarkerOptions()
                .title("Ksiba")
                .snippet("The most wonderful.")
                .position(KSIBA));
        markerKhnis = mMap.addMarker(new MarkerOptions()
                .title("Khnis")
                .snippet("The most wonderful.")
                .position(KHNIS));
        markerFrina = mMap.addMarker(new MarkerOptions()
                .title("Frina")
                .snippet("The most wonderful.")
                .position(FRINA));
        markerMonastirZI = mMap.addMarker(new MarkerOptions()
                .title("Monastir Zone Indistruelle")
                .snippet("The most wonderful.")
                .position(MONASTIRZI));

        markerFac2 = mMap.addMarker(new MarkerOptions()
                .title("Faculté 2")
                .snippet("The most wonderful.")
                .position(FAC2));
        markerMonastir = mMap.addMarker(new MarkerOptions()
                .title("Monastir")
                .snippet("The most wonderful.")
                .position(MONASTIR));
        markerMonastir.setIcon(BitmapDescriptorFactory.fromResource(ic_metro_512));

        markerFac = mMap.addMarker(new MarkerOptions()
                .title("Faculté")
                .snippet("The most wonderful.")
                .position(FAC));
        markerAeroport = mMap.addMarker(new MarkerOptions()
                .title("Aeroport")
                .snippet("The most wonderful.")
                .position(AEROPORT));
        markerHotels = mMap.addMarker(new MarkerOptions()
                .title("Les Hotels")
                .snippet("The most wonderful.")
                .position(HOTELS));
        markerSahlineSabkha = mMap.addMarker(new MarkerOptions()
                .title("Sahline Ville")
                .snippet("The most wonderful.")
                .position(SAHLINESABKHA));
        markerSahlineVille = mMap.addMarker(new MarkerOptions()
                .title("Sahline Ville")
                .snippet("The most wonderful.")
                .position(SAHLINEVILLE));
        markerSousseZI = mMap.addMarker(new MarkerOptions()
                .title("Sousse Zone Industrielle")
                .snippet("The most wonderful.")
                .position(SOUSSEZI));
        markerDepot = mMap.addMarker(new MarkerOptions()
                .title("Depot")
                .snippet("The most wonderful.")
                .position(DEPOT));
        markerSousseSud = mMap.addMarker(new MarkerOptions()
                .title("Sousse Sud")
                .snippet("The most wonderful.")
                .position(SOUSSESUD));
        markerMed5 = mMap.addMarker(new MarkerOptions()
                .title("Med V")
                .snippet("The most wonderful.")
                .position(MED5));
        markerBebJdid = mMap.addMarker(new MarkerOptions()
                .title("Beb Jdid")
                .snippet("The most wonderful.")
                .position(BEBJDID));
        markerBebJdid.setIcon(BitmapDescriptorFactory.fromResource(ic_metro_512));
        markerTrain=mMap.addMarker(new MarkerOptions()
        .title("Votre Train est ici")
        .position(new LatLng((attr1),attr2)));
        markerTrain.setIcon(BitmapDescriptorFactory.fromResource(ic_maps_directions_transit));
        MainActivity mainActivity = new MainActivity();
        marker = mMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(new LatLng(0, 0)));
        marker.setIcon(BitmapDescriptorFactory.fromResource(ic_maps_directions_walk));
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
        Intent inte = getIntent();
        Bundle bd = inte.getExtras();
        // int getIndexDep = (Integer) bd.get("indicedep");
        //   int getIndexarr = (Integer) bd.get("indicearr");
//        String getDep = (String) bd.get("GareDep");
        //       String getArr = (String) bd.get("Gareearr");
    /*    mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(lat(getDep),lon(getDep)))
                .add(new LatLng(lat(getArr),lon(getArr)))
                .color(R.color.mauve)
        );*/
        String depar, arriv;
        depar = mainActivity.getGareDepart();
        arriv = mainActivity.getGareArrivee();
        CameraPosition cp = CameraPosition.builder()
                .target(new LatLng(lat(depar), lon(depar)))
                .zoom(500000000)
                .bearing(90)
                .build();
        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_RED);
        addIcon(iconFactory, depar, new LatLng(lat(depar), lon(depar)));

        iconFactory.setStyle(IconGenerator.STYLE_GREEN);
        addIcon(iconFactory, arriv, new LatLng(lat(arriv), lon(arriv)));

       /* iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Baghdadi", BAGH);*/

    /*    //  iconFactory.setRotation(90);
        iconFactory.setStyle(IconGenerator.STYLE_RED);
        addIcon(iconFactory, "Teboulba", TBL);

        //   iconFactory.setContentRotation(-90);
        iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
        addIcon(iconFactory, "Moknine", MKN);

        // iconFactory.setRotation(0);
        //iconFactory.setContentRotation(90);
        iconFactory.setStyle(IconGenerator.STYLE_GREEN);
        addIcon(iconFactory, "Gare H.Bourguiba", MONASTIR);

        //  iconFactory.setRotation(0);
        //iconFactory.setContentRotation(0);
        iconFactory.setStyle(IconGenerator.STYLE_ORANGE);
        addIcon(iconFactory, "Borj Arif", BorjArif);*/
        //   showDistance();
        difference();
        //     final Handler handler = new Handler();

    }



    private double showDistance(Marker m) {
        double distance = SphericalUtil.computeDistanceBetween(getMarker().getPosition(), m.getPosition());
      //  Toast.makeText(MapsActivity.this, "la distance est" + formatNumber(distance), Toast.LENGTH_LONG).show();
        Log.d(TAG, "showDistance: "+formatNumber(distance));
        return distance;
    }
    private void difference() {
        double next;
         minName="";
        int i = 0;
        double mindist=0.00;
        ArrayList<Marker> listM = new ArrayList<>();
     //   listM.add(getMarker());
        listM.add(markerGare);
        listM.add(markerBorjArif);
        listM.add(markerEzzahra);
        listM.addAll(Arrays.asList(markerSidiMessaoud,markerBaghdadi,markerMahdiaZT,markerTeboulba,markerTeboulbaZI,markerBekalta,markerMonkine,markerMoknineGribaa,markerKsarHelal,markerKsarHelalZI,markerSayyada,markerLamta,
                markerBouhdjar,markerKsiba,markerKhnis,markerFrina,markerMonastirZI,markerFac2,markerMonastir,markerFac,markerAeroport,markerHotels,markerSahlineSabkha,markerSahlineVille,markerSousseZI,markerDepot,markerSousseSud,markerMed5,markerBebJdid));
        mindist = showDistance(listM.get(0));
        minName=listM.get(0).getTitle();
        for (i=0;i<listM.size()-1;i++) {
               next=showDistance(listM.get(i + 1));
               if ((Double.valueOf(next) < Double.valueOf(mindist))) {
                   mindist = next;
                   minName = listM.get(i + 1).getTitle();
               }
           }
timer();
    }
    private void timer(){
        final Handler handler = new Handler();
        Timer    timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                           affichage();
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 60000);
    }
    public void affichage(){
        java.sql.Time timeValueFromCuror = null;
        java.sql.Time timeValueNow = null;
        Intent in = getIntent();
        Bundle bd = in.getExtras();
        String getTime = (String) bd.get("tempDeDepart");
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        String timeStamp = formatter.format(Calendar.getInstance().getTime());
        try {
            timeValueNow = new java.sql.Time(formatter.parse(timeStamp).getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            timeValueFromCuror = new java.sql.Time(formatter.parse(getTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
         heures=0;
         minutes=0;
        long ecartEnMinutes = Math.abs( timeValueFromCuror.getTime()-timeValueNow.getTime() )/ 60000;

        if (ecartEnMinutes>60)
        {
            heures=ecartEnMinutes/60;
            minutes=ecartEnMinutes-(heures*60);
        }else
        {
            minutes=ecartEnMinutes;
        }
        Log.d(TAG, "difference de temp: "+ecartEnMinutes);
        mTextView.setText(makeCharSequence("La Plus Proche Station est ",minName));
        if(timeValueFromCuror.after(timeValueNow)) {
            mTextViewTime.setText(makeCharSequence("Il Vous Reste:" + " ",String.valueOf(heures + " " + "Heures")+" "+ String.valueOf(minutes + " " + "Minutes")));
        }
        if(timeValueFromCuror.before(timeValueNow)) {
            mTextViewTime.setText(makeCharSequence("ce train est parti il y a:" + " ",String.valueOf(heures + " " + "Heures")+" "+ String.valueOf(minutes + " " + "Minutes")));

            mToggle.setEnabled(false);
        }
  /*      mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(heures==0 && minutes==0) {
                    mToggle.setChecked(true);
                    generateNotification(MapsActivity.this, "Votre métro est en place");
                    Log.d(TAG, "notification: ");

                }
            }
        });*/

        if(mToggle.isChecked()==true){

            if(heures==0 && minutes==0) {

                generateNotification(MapsActivity.this, "Votre métro est en place");
                Log.d(TAG, "notification: ");

            }
        }


        Log.d(TAG, "text: "+mTextView);
    //    Toast.makeText(MapsActivity.this,"la plus proche station est"+" "+minName,Toast.LENGTH_LONG).show();
    }
    private CharSequence makeCharSequence(String prefix,String suffix) {
       // String prefix = "La Plus Proche Station est: ";
       // String suffix = minName;
        String sequence = prefix + suffix;
        SpannableStringBuilder ssb = new SpannableStringBuilder(sequence);
        ssb.setSpan(new StyleSpan(ITALIC), 0, prefix.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new StyleSpan(BOLD), prefix.length(), sequence.length(), SPAN_EXCLUSIVE_EXCLUSIVE);

        return ssb;
    }
    private String formatNumber(double distance) {
        String unit = "m";
        if (distance < 1) {
            distance *= 1000;
            unit = "mm";
        } else if (distance > 1000) {
            distance /= 1000;
            unit = "km";
        }
        return String.format("%4.3f%s", distance, unit);
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

    public Marker getMarker() {
        return marker;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        Log.d(TAG, "request:done");
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
    public void onLocationChanged(Location location) {
        lon = location.getLongitude();
        lat = location.getLatitude();
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        marker.setPosition(latLng);
        Log.d(TAG, "onLocationChanged: ");
     //   Toast.makeText(this, "longitude" + lon + "lattitude" + lat, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
     //   Toast.makeText(this, "client connecté", Toast.LENGTH_SHORT).show();
    }

    @Override

    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        savePreferences();
    }

   @Override
    protected void onResume() {
        super.onResume();

    }
    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        mMap.addMarker(markerOptions);
    }
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                onMapReady(getmMap());
            }
        }
    }

    public long getMinutes() {
        return minutes;
    }

    public long getHeures() {
        return heures;
    }

    public void generateNotification(Context context, String message) {

        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();

        Intent intent = new Intent(context, Context.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
       .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })


                .setSmallIcon(icon)
                .setTicker("SNCFT Mobile")
                .setContentTitle("SNCFT Mobile")
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());

    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }
    private String getJSONString(Context context)
    {
        String str = "";
        try
        {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open("train.json");
            InputStreamReader isr = new InputStreamReader(in);
            char [] inputBuffer = new char[100];

            int charRead;
            while((charRead = isr.read(inputBuffer))>0)
            {
                String readString = String.copyValueOf(inputBuffer,0,charRead);
                str += readString;
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        return str;
    }
    public void parseJSON() throws JSONException
    {
        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(getJSONString(getApplicationContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        json = json.getJSONObject("FirstTrain");
         attr1 = json.getDouble("lat");
         attr2 = json.getDouble("lon");



}

}

