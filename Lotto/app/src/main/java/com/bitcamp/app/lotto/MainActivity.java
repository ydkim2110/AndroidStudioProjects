package com.bitcamp.app.lotto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView result = findViewById(R.id.result);

        findViewById(R.id.create_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Random random = new Random();
               String res = "";
               int[] a = new int[45];

               for(int i=0; i<45; i++) {
                    a[i] = i+1;
               }

               int tmp = 0;
               int j = 0;

               for(int i=0; i<100; i++) {
                   j = (int) (Math.random()*45);
                   tmp = a[0];
                   a[0] = a[j];
                   a[j] = tmp;
               }

               for(int i=0; i<6; i++) {
                    res += a[i]+" ";
                   /*        if(i != 5) {
                       res += a[i] + ", ";
                   } else {
                       res += a[i];
                   }*/
               }
               result.setText("결과: "+res);
            }
        });
    }
}
