package com.bitcamp.app.kakao.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Email {
    private Context context;
    private Activity activity;

    public Email(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void sendEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"+email));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "HELLO GUYS!!");
        intent.putExtra(Intent.EXTRA_TEXT, "잘 지내냐? 연락줘라");
        context.startActivity(intent.createChooser(intent, "이메일"));
    }
}
