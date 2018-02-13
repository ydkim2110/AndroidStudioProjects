package com.bitcamp.app.kakao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bitcamp.app.kakao.R;
import com.bitcamp.app.kakao.activity.Intro.*;
import java.util.ArrayList;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        final Context context = MemberList.this;
        final ListView listView = findViewById(R.id.listView);
        
        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MemberAdd.class));
            }
        });
        listView.setAdapter(new MemberItem(context,
                (ArrayList<Member>) new Intro.ListService() {
                    @Override
                    public ArrayList<?> execute() {
                        return new MemberItemList(context).list();
                    }
                }.execute()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Member selectedMember = (Member) listView.getItemAtPosition(pos);
                Toast.makeText(context, selectedMember.name+"님의 상세로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MemberDetail.class);
                intent.putExtra(Intro.MEMBER_1, selectedMember.id);
                intent.putExtra(Intro.MEMBER_3, selectedMember.name);
                intent.putExtra(Intro.MEMBER_5, selectedMember.phoneNumber);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                final Member deleteMember = (Member) listView.getItemAtPosition(pos);
                new AlertDialog.Builder(context).setTitle("DELEDTE").setMessage("정말로 삭제할까요?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "삭제 실행합니다.", Toast.LENGTH_LONG).show();
                        final MemberItemDelete itemDelete = new MemberItemDelete(context);
                        new DMLService(){
                            @Override
                            public void execute() {
                                itemDelete.delete(deleteMember);
                            }
                        }.execute();
                        startActivity(new Intent(context, MemberList.class));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "삭제 취소합니다.", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
                return true;
            }
        });
        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Index.class));
            }
        });

    }
    private abstract class DeleteQuery extends QueryFactory {
        SQLiteHelper helper;
        public DeleteQuery(Context context) {
            super(context);
            helper = new SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() { return helper.getWritableDatabase();}
    }
    private class MemberItemDelete extends DeleteQuery {
        public MemberItemDelete(Context context) {
            super(context);
        }
        public void delete(Member member) {
            this.getDatabase().execSQL(String.format(
                    "DELETE FROM %s WHERE " +
                            "%s Like '%s';",
                    Intro.TABLE_MEMBER,
                    Intro.MEMBER_1,
                    member.id));

        }
    }

    private abstract class ListQuery extends QueryFactory {
        SQLiteHelper helper;
        public ListQuery(Context context) {
            super(context);
            helper = new SQLiteHelper(context);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class MemberItemList extends ListQuery {
        public MemberItemList(Context context) {
            super(context);
        }
        public ArrayList<Member> list() {
            ArrayList<Member> members = new ArrayList<>();
            String sql = String.format(
                    "SELECT %s, %s, %s, %s from %s ", Intro.MEMBER_6,
                    Intro.MEMBER_3,
                    Intro.MEMBER_5,
                    Intro.MEMBER_1,
                    Intro.TABLE_MEMBER);
            Cursor cursor = this.getDatabase().rawQuery(sql, null);
            Member member = null;
            if(cursor != null) {
                while (cursor.moveToNext()) {
                    member = new Member();
                    member.profilePhoto = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_6));
                    member.name = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_3));
                    member.phoneNumber = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_5));
                    member.id = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_1));
                    members.add(member);
                }
            }
            Log.d("0 : ", members.get(0).profilePhoto);
            return members;
        }
    }
    private class MemberItem extends BaseAdapter {
        ArrayList<Member> list;
        LayoutInflater inflater;
        public MemberItem(Context context, ArrayList<Member> list) {
            this.list = list;
            this.inflater = LayoutInflater.from(context);
        }
        private int[] photo = {
            R.drawable.profile_1,
            R.drawable.profile_2,
            R.drawable.profile_3,
            R.drawable.profile_4,
            R.drawable.profile_5,
            R.drawable.profile_6,
            R.drawable.profile_9
        };
        // 리스트뷰에게 리스트의 개수를 몇개로 구성해야 하는지를 알게 해준다.
        @Override
        public int getCount() {
            return list.size();
        }
        // 리스트뷰에게 지정한 position 변수에 해당하는 순서의 데이터를 추출 할 수 있도록 해준다.
        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
        // 리스트뷰는 getView 메소드를 getCount 반환 값만큼 호출하여, 각 item을 구성하게 된다.
        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null) {
                v = inflater.inflate(R.layout.member_item, null);
                holder = new ViewHolder();
                holder.profilePhoto = v.findViewById(R.id.profile_photo);
                holder.name = v.findViewById(R.id.name);
                holder.phoneNumber = v.findViewById(R.id.phone_number);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            holder.profilePhoto.setImageResource(photo[i]);
            holder.name.setText(list.get(i).name);
            holder.phoneNumber.setText(list.get(i).phoneNumber);

            return v;
        }
    }
    static class ViewHolder {
        ImageView profilePhoto;
        TextView name;
        TextView phoneNumber;
    }
}
