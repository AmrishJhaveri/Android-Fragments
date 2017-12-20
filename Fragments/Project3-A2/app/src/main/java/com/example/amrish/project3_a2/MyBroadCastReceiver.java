package com.example.amrish.project3_a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Amrish on 30-Oct-17.
 */

public class MyBroadCastReceiver extends BroadcastReceiver {

    @Override
    /**
     * this will be called when both A1 and A2 have the permissions before sending the broadcast.
     */
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"In broadcast receiver",Toast.LENGTH_LONG).show();

        //Create an intent to start the gallery app activity
        Intent intentGallery=new Intent(context,MainActivity.class);
        context.getApplicationContext().startActivity(intentGallery);

    }
}
