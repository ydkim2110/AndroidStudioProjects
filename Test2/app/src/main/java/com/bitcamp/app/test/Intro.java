package com.bitcamp.app.test;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Intro extends AppCompatActivity {
    SQLiteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        final Context context = Intro.this;
        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper = new SQLiteHelper(context);
                startActivity(new Intent(context, Index.class));
            }
        });
    }
    static final String DATABASE_NAME = "kakao.db";
    static final String TABLE_MEMBER = "member";
    static final String MEMBER_1 = "id";
    static final String MEMBER_2 = "password";
    static final String MEMBER_3 = "name";
    static final String MEMBER_4 = "email";
    static final String MEMBER_5 = "phone_number";
    static final String MEMBER_6 = "profile_photo";
    static final String MEMBER_7 = "address";
    static class Member {
        String id, password, name, email, phoneNumber, profilePhoto, address;
        public String toString() {
            return id+","+password+","+name+","+email+","+phoneNumber+","+profilePhoto+","+address;
        }
    }
    static abstract class QueryFactory  {
        Context context;
        public QueryFactory(Context context) {
            this.context = context;
        }
        public abstract SQLiteDatabase getDatabase();
    }

    static class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(String.format(" CREATE TABLE IF NOT EXISTS %s( " +
                            " %s INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT );", TABLE_MEMBER, MEMBER_1, MEMBER_2, MEMBER_3,
                    MEMBER_4, MEMBER_5, MEMBER_6, MEMBER_7));
            for(int i=1; i<6; i++) {
                db.execSQL(
                        String.format(" INSERT INTO %s ("+
                                        " %s, %s, %s, %s, %s, %s)"+
                                        " VALUES('%s', '%s', '%s', '%s', '%s', '%s');",
                                TABLE_MEMBER, MEMBER_2, MEMBER_3,
                                MEMBER_4, MEMBER_5, MEMBER_6, MEMBER_7,
                                "1", "홍길동"+i, "hong"+i+"@gmail.com", "010-3453-443"+i, "profile_"+i,
                                "서울 백범로 "+i+"길"));
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_MEMBER);
            onCreate(db);
        }
    }
}
