package com.theluckydev.fst;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class SalleLibre extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle_libre);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("https://libreoupas.000webhostapp.com/");
    }
}
