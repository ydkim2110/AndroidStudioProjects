package com.bitcamp.app.kakao.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.bitcamp.app.kakao.R;

public class Message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        final Context context = Message.this;
        WebView webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new Object(){
            @android.webkit.JavascriptInterface
            public String toString() {
                return "Hybrid";
            }
            @android.webkit.JavascriptInterface
            public void showToast(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
            @android.webkit.JavascriptInterface
            public void sendSMS(String phone, String message) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phone, null, message, null, null);
            }
        }, "Hybrid");
        webView.loadUrl("file:///android_asset/www/index.html");



    }
}
