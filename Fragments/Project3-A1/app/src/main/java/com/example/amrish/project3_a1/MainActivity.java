package com.example.amrish.project3_a1;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LandmarkListFragment.ListSelectionListener {

    /**
     * This are required for broadcasting with permisssions
     */
    private static final String GALLERY_PERMISSIONS_A2 = "com.example.amrish.project3_a2.PERMISSION_GALLERY_AMRISH";
    private static final String GALLERY_ACTION_A2 = "com.example.amrish.project3_a2.ACTION_GALLERY_AMRISH";
    private static final int GALLERY_PERMISSION_REQUEST_CODE_A2 = 1;

    /**
     * Required for working with fragments and framelayouts
     */
    private FrameLayout mLandmarkWebViewFragmentLayout;
    private FragmentManager mFragmentManager;
    private LandmarkWebViewFragment mWebViewFragment;
    private LandmarkListFragment mListFragment;

    /**
     * Required for invoking the deselection on the list fragment
     */
    private static int currentSelection = -1;

    /**
     * TAG for retaining the web view fragment
     */
    private static final String TAG_RETAINED_FRAGMENT_WEBVIEW = "RetainedWebViewFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieve the framelayout
        mLandmarkWebViewFragmentLayout = (FrameLayout) findViewById(R.id.landmark_webview_fragment_container);

        // get the fragemnt manager
        mFragmentManager = getFragmentManager();

        //Retrieve the static fragment
        mListFragment = (LandmarkListFragment) mFragmentManager.findFragmentById(R.id.landmark_list_fragment_container);

        //Retrieve the dynamic fragment by TAG
        mWebViewFragment = (LandmarkWebViewFragment) mFragmentManager.findFragmentByTag(TAG_RETAINED_FRAGMENT_WEBVIEW);

        // If the retained fragment is null, then just initialize it
        if (mWebViewFragment == null) {
            //Toast.makeText(getApplicationContext(), "webview is null", Toast.LENGTH_SHORT).show();
            mWebViewFragment = new LandmarkWebViewFragment();
        } else {
            // else the fragment should be visible in the new orientation, so set the layout
            setLayout();
            //load the url of the page
            mWebViewFragment.showNewWebView(mWebViewFragment.getCurrentWebView());
        }

        //Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                setLayout();
            }
        });

        //for the action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    private void setLayout() {

        //if landscape mode do some changes
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            //if webview fragment is added then show the list & webview in 1:2 ratio of the screen
            if (mWebViewFragment.isAdded()) {
                //Toast.makeText(getApplicationContext(), "back listener", Toast.LENGTH_SHORT).show();
                mLandmarkWebViewFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));
                mListFragment.getView().setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

            } else {
                //if web view is not added then show just the list fragment
                mLandmarkWebViewFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Do Changes if the orientation is portrait

            // only show the web view fragment if it is added
            if (mWebViewFragment.isAdded()) {
                //Toast.makeText(getApplicationContext(), "back listener", Toast.LENGTH_SHORT).show();
                mLandmarkWebViewFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));
                mListFragment.getView().setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0f));

            } else {
                //else just show the list fragment
                mLandmarkWebViewFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
                mListFragment.getView().setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            }

        }
        //Toast.makeText(getApplicationContext(), mListFragment.isAdded() +" " + !mWebViewFragment.isAdded(), Toast.LENGTH_SHORT).show();
        //If the list fragment is added and webview is not , then deselect the choice
        if (mListFragment.isAdded() && !mWebViewFragment.isAdded()) {
            //Toast.makeText(getApplicationContext(), "back stack", Toast.LENGTH_SHORT).show();
            mListFragment.deselectChoice(currentSelection);
        }
    }


    @Override
    /*
    Called from the list fragment when a list item is selected. So the activity can take appropriate action such as passing the information to fragment2
     */
    public void onListSelection(int index) {

        // if webview fragment is not added then add it and put it on the back stack
        if (!mWebViewFragment.isAdded()) {
            //Toast.makeText(getApplicationContext(), "" + index, Toast.LENGTH_SHORT).show();

            // get the fragment transcation from the fragment manager
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

            //add the fragment to the framelayout with a TAG
            mFragmentTransaction.add(R.id.landmark_webview_fragment_container, mWebViewFragment, TAG_RETAINED_FRAGMENT_WEBVIEW);
            //mFragmentTransaction.replace(R.id.landmark_webview_fragment_container, mWebViewFragment);

            //put the current transcation on a back stack.
            mFragmentTransaction.addToBackStack(null);

            //commit the transaction
            mFragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        // If the current index shown and the selected index are separate then show the webpage of the new index.
        if (index != mWebViewFragment.getCurrentWebView()) {
            //Toast.makeText(getApplicationContext(), "if: " + index, Toast.LENGTH_SHORT).show();
            setLayout();
            currentSelection = index;
            mWebViewFragment.showNewWebView(index);
        }
    }

    @Override
    /**
     * For the action bar
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    /**
     * Action bar actions for exiting A1 and launching A2
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.exit_a1:
                Toast.makeText(getApplicationContext(), "Exiting A1", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.launch_a2:

                //Checking if A1 has the permissions
                int permissionCheck = ContextCompat.checkSelfPermission(this, GALLERY_PERMISSIONS_A2);

                //Toast.makeText(getApplicationContext(), "permissionCheck: " + permissionCheck, Toast.LENGTH_SHORT).show();

                //If denied then ask for the permissions from the user
                if (PackageManager.PERMISSION_DENIED == permissionCheck) {
                    //Toast.makeText(getApplicationContext(), "Falied: " + permissionCheck, Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(this,
                            new String[]{GALLERY_PERMISSIONS_A2},
                            GALLERY_PERMISSION_REQUEST_CODE_A2);
                } else {
                    //If permissions present then broadcast
                    sendCustomizedBroadcast();
                }
                //Toast.makeText(getApplicationContext(), "launch", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    /**
     * Response of the user to the permission request is received here
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case GALLERY_PERMISSION_REQUEST_CODE_A2:
                // If permissions are granted then send the broadcast.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted!", Toast.LENGTH_SHORT).show();
                    sendCustomizedBroadcast();
                } else {
                    //If denied show a toast
                    Toast.makeText(getApplicationContext(), "Permission denied to the Gallery App!", Toast.LENGTH_SHORT).show();
                    //finish();
                }

                break;
            default:
                Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * Create the intent and send the broadcast
     */
    private void sendCustomizedBroadcast() {
        Intent t = new Intent(GALLERY_ACTION_A2);

        sendBroadcast(t, GALLERY_PERMISSIONS_A2);
    }
}

