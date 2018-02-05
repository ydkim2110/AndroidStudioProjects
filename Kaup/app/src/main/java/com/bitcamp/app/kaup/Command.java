package com.bitcamp.app.kaup;

import android.widget.EditText;

/**
 * Created by 1027 on 2018-02-05.
 */

public class Command {
    public static String calc(EditText name, EditText weight, EditText height) {
        double tmp = 0;
        String result = "";
                    tmp = Double.parseDouble(String.valueOf(weight.getText()))/
                            (Double.parseDouble(String.valueOf(height.getText()))
                                *Double.parseDouble(String.valueOf(height.getText()))/10000);
        if (tmp >= 35) {
            result ="결과: "+String.valueOf(name.getText())+" 님은 고도 비만 입니다.";
        } else if (tmp >= 30) {
            result ="결과: "+ String.valueOf(name.getText())+" 님은 중등도 비만 입니다.";
        } else  if (tmp >= 25) {
            result ="결과: "+ String.valueOf(name.getText())+" 님은 경도 비만 입니다.";
        } else  if (tmp >= 23) {
            result ="결과: "+ String.valueOf(name.getText())+" 님은 과체중 입니다.";
        } else  if (tmp >= 18.5) {
            result ="결과: "+ String.valueOf(name.getText())+" 님은 정상 입니다.";
        } else {
            result ="결과: "+ String.valueOf(name.getText())+" 님은 저체중 입니다.";
        }

        return result;
    }
}
