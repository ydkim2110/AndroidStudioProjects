package com.bitcamp.app.kakao.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitcamp.app.kakao.R;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);
        final Context context = MemberUpdate.this;
        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, Index.class));
            }
        });
    }
}
