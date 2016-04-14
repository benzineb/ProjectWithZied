package com.example.user.projectwithzied;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String[] arraySpinner;
    static final int dp=0;
    static final int arr=0;
//Override
 /*   public void onSaveInstanceState(Bundle outState) {
      //  outState.putInt(dp);


        super.onSaveInstanceState(outState);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button partir= (Button) findViewById(R.id.maintenant);
        Button partirpl= (Button) findViewById(R.id.maintenant);
        this.arraySpinner = new String[] {
                "Mahdia La Gare ", "EZZAHRA", "Borj Arif", "Sidi Messaoud", "Zone Touristique"
        };
       final  Spinner dep = (Spinner) findViewById(R.id.spinner);
        final Spinner arr = (Spinner) findViewById(R.id.spinnerarr);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        dep.setAdapter(adapter);
        arr.setAdapter(adapter);
        partir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dep.getSelectedItem()==arr.getSelectedItem()){
                    Toast.makeText(MainActivity.this,"la station du départ doit être differente de l'arrivée ",Toast.LENGTH_LONG).show();
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
}
