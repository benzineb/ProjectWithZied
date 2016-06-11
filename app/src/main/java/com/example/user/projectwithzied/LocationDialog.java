package com.example.user.projectwithzied;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by User on 08/06/2016.
 */
public class LocationDialog extends DialogFragment {
    private static String TAG="dialog";
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.messagedialog)
                .setNegativeButton(R.string.negatif, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // finish();
                       // Intent intent = new Intent(MapsActivity.class, MapsActivity.class);
                      //  startActivity(intent);

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}