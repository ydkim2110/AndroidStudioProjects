package com.bitcamp.app.kakao.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.bitcamp.app.kakao.activity.Intro.*;
import com.bitcamp.app.kakao.R;
import static com.bitcamp.app.kakao.activity.MemberDetail.getRoundedRectBitmap;

public class MemberUpdate extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);
        final Context context = MemberUpdate.this;
        final EditText editPass = findViewById(R.id.edit_pass);
        final EditText editName = findViewById(R.id.edit_name);
        final EditText editPhoneNumber = findViewById(R.id.edit_phone_number);
        final EditText editEmail = findViewById(R.id.edit_email);
        final EditText editAddress = findViewById(R.id.edit_address);
        final ImageView profilePhoto = findViewById(R.id.profile_photo);
        final Intent intent = this.getIntent();

        final String[] memberInfo = intent.getStringExtra("member").split(",");

        editPass.setHint(memberInfo[1]);
        editName.setHint(memberInfo[2]);
        editEmail.setHint(memberInfo[3]);
        editPhoneNumber.setHint(memberInfo[4]);
        editAddress.setHint(memberInfo[6]);

        BitmapFactory.Options options= new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap orgImage = BitmapFactory.decodeResource(getResources(),
                this.getResources()
                        .getIdentifier(
                                this.getPackageName()+":drawable/"
                                        +memberInfo[5],
                                null, null), options);
        Bitmap resize = Bitmap.createScaledBitmap(orgImage, 200, 200, true);
        Bitmap circle = getRoundedRectBitmap(resize, 100);
        profilePhoto.setImageBitmap(circle);

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Log.d("변경하려는 PASS", String.valueOf(editPass.getText()));
            Log.d("변경하려는 NAME : ", String.valueOf(editName.getText()));
            Log.d("변경하려는 PHONE : ", String.valueOf(editPhoneNumber.getText()));
            Log.d("변경하려는 EMAIL : ", String.valueOf(editEmail.getText()));
            Log.d("변경하려는 ADDRESS : ", String.valueOf(editAddress.getText()));

            final Member changeMember = new Member();
            changeMember.id = memberInfo[0];
            changeMember.password = (editPass.getText().toString().equals("")) ?
                memberInfo[1] : editPass.getText().toString();
            changeMember.name = (editName.getText().toString().equals("")) ?
                memberInfo[2] : editName.getText().toString();
            changeMember.email = (editEmail.getText().toString().equals("")) ?
                memberInfo[3] : editEmail.getText().toString();
            changeMember.phoneNumber = (editPhoneNumber.getText().toString().equals("")) ?
                memberInfo[4] : editPhoneNumber.getText().toString();
            changeMember.address = (editAddress.getText().toString().equals("")) ?
                memberInfo[6] : editAddress.getText().toString();
            final MemberItemUpdate itemUpdate = new MemberItemUpdate(context);
            new DMLService() {
                @Override
                public void execute() {
                    itemUpdate.update(changeMember);
                }
            }.execute();

            Intent intent1 = new Intent(context, MemberDetail.class);
            intent1.putExtra(Intro.MEMBER_1, memberInfo[0]);
            startActivity(intent1);
              }
        });

        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MemberList.class));
            }
        });

    }

    private abstract class UpdateQuery extends QueryFactory { // 추상클래스는 객체를 만들 수가 없음
        SQLiteHelper helper;
        public UpdateQuery(Context context) {
            super(context);
            helper = new SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() { return helper.getWritableDatabase();}
    }

    private class MemberItemUpdate extends UpdateQuery {

        public MemberItemUpdate(Context context) { super(context);}

        public void update(Member member) {
            String sql = String.format(
                    "UPDATE %s " +
                    "SET %s = '%s', "+
                    "%s = '%s', "+
                    "%s = '%s', "+
                    "%s = '%s', "+
                    "%s = '%s' "+
                    "WHERE %s LIKE %s;",
                        Intro.TABLE_MEMBER,
                        Intro.MEMBER_2, member.password,
                        Intro.MEMBER_3, member.name,
                        Intro.MEMBER_4, member.email,
                        Intro.MEMBER_5, member.phoneNumber,
                        Intro.MEMBER_7, member.address,
                        Intro.MEMBER_1, member.id);
            this.getDatabase().execSQL(sql);
        }
    }
}
