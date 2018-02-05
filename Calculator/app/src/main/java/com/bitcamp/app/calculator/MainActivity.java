package com.bitcamp.app.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText num1 = findViewById(R.id.input_num1);
        final EditText num2 = findViewById(R.id.input_num2);
        final TextView result = findViewById(R.id.result);
        findViewById(R.id.plus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText(Command.calc(num1, num2, "+"));
            }
        });

        findViewById(R.id.minus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.multiple_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.divide_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
