package com.bitcamp.app.calculator;

import android.widget.EditText;

/**
 * Created by 1027 on 2018-02-05.
 */

public class Command {
    public static int changeInt(EditText num) {
        return Integer.parseInt(String.valueOf(num.getText()));
    }
    public static String calc(EditText num1, EditText num2, String str) {
        int result = 0;
            switch (str) {
                case "+":
                    result = Integer.parseInt(String.valueOf(num1.getText()))
                                +Integer.parseInt(String.valueOf(num2.getText()));
                    break;
            }
        return "계산결과: "+result;
    }
}

