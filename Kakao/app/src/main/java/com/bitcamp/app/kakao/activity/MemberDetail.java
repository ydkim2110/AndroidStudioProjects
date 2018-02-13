package com.bitcamp.app.kakao.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bitcamp.app.kakao.activity.Intro.*;
import com.bitcamp.app.kakao.R;
import com.bitcamp.app.kakao.util.Email;
import com.bitcamp.app.kakao.util.Phone;

public class MemberDetail extends AppCompatActivity {
    Phone phone;
    Email email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        final Context context = MemberDetail.this;
        phone = new Phone(context, this);
        email = new Email(context, this);
        Intent intent = this.getIntent();
        final String id = intent.getStringExtra(Intro.MEMBER_1);
        final MemberItem item = new MemberItem(context);
        final Member member = (Member) new DetailService() {
            @Override
            public Object execute() {
                return item.detail(id);
            }
        }.execute();
        final TextView dName = findViewById(R.id.detail_name);
        final TextView dPhoneNumber = findViewById(R.id.phone_number);
        dName.setText(member.name);
        dPhoneNumber.setText(member.phoneNumber);
        final ImageView dProfPhoto = findViewById(R.id.detail_profile);
        BitmapFactory.Options options= new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap orgImage = BitmapFactory.decodeResource(getResources(),
                this.getResources()
                        .getIdentifier(
                                this.getPackageName()+":drawable/"
                                        +member.profilePhoto,
                                null, null), options);
        Bitmap resize = Bitmap.createScaledBitmap(orgImage, 200, 200, true);
        Bitmap circle = getRoundedRectBitmap(resize, 100);
        dProfPhoto.setImageBitmap(circle);
        findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MemberUpdate.class);
                intent.putExtra("member", member.toString());
                startActivity(intent);
            }
        });
        findViewById(R.id.call_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone.dial(member.phoneNumber);
            }
        });
        findViewById(R.id.email_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.sendEmail(member.email);
            }
        });
        findViewById(R.id.album_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Album.class));
            }
        });
    }
    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(100, 100, 200, 200);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(150, 150, 50, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }

    private abstract class DetailQuery extends QueryFactory {
        SQLiteHelper helper;
        public DetailQuery(Context context) {
            super(context);
            helper = new Intro.SQLiteHelper(context);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class MemberItem extends DetailQuery {
        Member member = null;
        public MemberItem(Context context) {
            super(context);
        }
        public Member detail(String id) {
            String sql = String.format(
                    "SELECT * FROM %s WHERE "+
                    "%s LIKE '%s';",
                    Intro.TABLE_MEMBER,
                    Intro.MEMBER_1,
                    id);
            Cursor cursor = this.getDatabase().rawQuery(sql, null);
            if(cursor != null) {
                cursor.moveToFirst();
                member = new Member();
                member.id = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_1));
                member.password = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_2));
                member.name = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_3));
                member.email = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_4));
                member.phoneNumber = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_5));
                member.profilePhoto = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_6));
                member.address = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_7));
            }
            Log.d("검색한 회원의 이름: ", member.name);
            Log.d("검색한 회원의 프로필 이미지: ", member.profilePhoto);
            return member;
        }
    }

}
