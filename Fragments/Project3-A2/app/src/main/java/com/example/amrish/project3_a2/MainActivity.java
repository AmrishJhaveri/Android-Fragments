package com.example.amrish.project3_a2;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

    /**
     * Required for permissions of A2
     */
    private static final String GALLERY_PERMISSIONS_A2 = "com.example.amrish.project3_a2.PERMISSION_GALLERY_AMRISH";
    private static final int GALLERY_PERMISSION_REQUEST_CODE_A2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Permissions are requested the first time the app is opened
         */
        requestRequiredPermissions();


    }

    private void requestRequiredPermissions() {
        //check for the permissions
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                GALLERY_PERMISSIONS_A2);

        //If denied then ask for the permissions
        if (PackageManager.PERMISSION_DENIED == permissionCheck) {
            ActivityCompat.requestPermissions(this,
                    new String[]{GALLERY_PERMISSIONS_A2},
                    GALLERY_PERMISSION_REQUEST_CODE_A2);
        } else {
            //If granted then show the gallery
            showGallery();
        }
    }

    /**
     * To show the gallery the adapter needs to be setup, which will be done only if the permission is granted
     */
    private void showGallery() {
        GridView gridview = (GridView) findViewById(R.id.grid_view);
        gridview.setAdapter(new GalleryAdapter(this));
    }

    @Override
    /**
     * Response of the request permissions
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case GALLERY_PERMISSION_REQUEST_CODE_A2:
                //IF the permissions are granted then show the gallery
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted!", Toast.LENGTH_SHORT).show();
                    showGallery();
                } else {
                    //If denied then show the message.
                    Toast.makeText(getApplicationContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), "Default Case", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}