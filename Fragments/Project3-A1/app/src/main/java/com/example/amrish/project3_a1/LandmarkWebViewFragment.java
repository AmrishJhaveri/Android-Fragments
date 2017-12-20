package com.example.amrish.project3_a1;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Amrish on 28-Oct-17.
 */
public class LandmarkWebViewFragment extends Fragment {

    private WebView webview;
    private int currentWebView = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //Creating an empty webView
        webview = new WebView(getActivity());

        //Enabling Javascript
        webview.getSettings().setJavaScriptEnabled(true);

        //If some website was open in the previous orientation than show the same in
        if (currentWebView != -1) {
            showNewWebView(currentWebView);
        }

        // Define a web view client so it does not require another app to open the webpage
        webview.setWebViewClient(new WebViewClient());

        //to retian this fragment across the orientation changes
        setRetainInstance(true);
        return webview;
    }

    public void showNewWebView(int index) {

        /**
         * If the indices are within a valid range then load the new url
         */
        if (index >= 0 && index < Constants.getLandmarkWebsites().length) {
            currentWebView = index;
            webview.loadUrl(Constants.getLandmarkWebsites()[index]);
        }
    }

    /**
     * Return the index of the current page being shown
     * @return int
     */
    public int getCurrentWebView() {
        return currentWebView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "LandmarkWebViewFragment oncreate", Toast.LENGTH_SHORT).show();
    }
}
