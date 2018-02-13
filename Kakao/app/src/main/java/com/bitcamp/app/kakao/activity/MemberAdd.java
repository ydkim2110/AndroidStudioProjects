package com.bitcamp.app.kakao.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.bitcamp.app.kakao.activity.Intro.*;
import com.bitcamp.app.kakao.R;

public class MemberAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_add);
        final ImageView dProfPhoto = findViewById(R.id.profile_photo);
        final Context context = MemberAdd.this;
        final EditText phoneNumber = findViewById(R.id.add_phoneNumber);
        final EditText name = findViewById(R.id.add_name);
        final EditText email = findViewById(R.id.add_email);
        final EditText address = findViewById(R.id.add_address);

        BitmapFactory.Options options= new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap orgImage = BitmapFactory.decodeResource(getResources(),
                this.getResources()
                        .getIdentifier(
                                this.getPackageName()+":drawable/"
                                        +"profile_0",
                                null, null), options);
        Bitmap resize = Bitmap.createScaledBitmap(orgImage, 600, 600, true);
        dProfPhoto.setImageBitmap(resize);

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Member member = new Member();
                member.phoneNumber = String.valueOf(phoneNumber.getText());
                member.name = String.valueOf(name.getText());
                member.email = String.valueOf(email.getText());
                member.address = String.valueOf(address.getText());
                final MemberItemInsert itemInsert = new MemberItemInsert(context);
                new DMLService() {
                    @Override
                    public void execute() {
                        itemInsert.insert(member);
                    }
                }.execute();
                startActivity(new Intent(context, MemberList.class));
            }
        });
        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private abstract class InsertQuery extends QueryFactory {
        SQLiteHelper helper;
        public InsertQuery(Context context) {
            super(context);
            helper = new SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {return helper.getWritableDatabase();}
    }

    private class MemberItemInsert extends InsertQuery {
        public MemberItemInsert(Context context) {
            super(context);
        }
        public void insert(Member member) {
            this.getDatabase().execSQL(String.format(
                    "INSERT INTO %s(%s, %s, %s, %s, %s, %s) "+
                            "VALUES('%s', '%s', '%s', '%s', '%s', '%s');",
                    Intro.TABLE_MEMBER,
                    Intro.MEMBER_5, Intro.MEMBER_3, Intro.MEMBER_4, Intro.MEMBER_7, Intro.MEMBER_2, Intro.MEMBER_6,
                    member.phoneNumber, member.name, member.email, member.address, 1, "profile_0"));
        }
    }

}
