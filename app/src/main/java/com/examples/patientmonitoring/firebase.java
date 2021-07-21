package com.examples.patientmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class firebase extends AppCompatActivity {
WebView w;
String url="https://console.firebase.google.com/u/5/project/patient-monitoring-syste-a177f/database/patient-monitoring-syste-a177f-default-rtdb/data";
    //String url="http://192.168.18.158:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        w =findViewById(R.id.web);
        w.setWebViewClient(new WebViewClient());
        w.getSettings().setJavaScriptEnabled(true);
        w.loadUrl(url);
    }
}