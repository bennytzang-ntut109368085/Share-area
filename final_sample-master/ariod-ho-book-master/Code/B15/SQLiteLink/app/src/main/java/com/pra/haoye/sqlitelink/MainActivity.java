package com.pra.haoye.sqlitelink;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{
    protected static final String DB_NAME = "HotlineDB";
    protected static final String TB_NAME = "hotlist";
    protected static final int MAX = 8;
    protected static final String[] FROM = new String[] {"name","phone","email","type"};
    protected SQLiteDatabase db;
    protected Cursor cur;
    protected SimpleCursorAdapter adapter;
    protected EditText etName, etPhone, etEmail, etype;
    protected Button btInsert, btUpdate, btDelete, btCall, btMail, clsbtnobj;
    protected ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Func();
    }

    private View.OnClickListener ClsBtn = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.this.onDestory();
        }
    };

    private void onDestory() {
        super.onDestroy();
        System.exit(0);
    }


    public void onInsertUpdate(View v){
        String nameStr=etName.getText().toString().trim();
        String phoneStr=etPhone.getText().toString().trim();
        String emailStr=etEmail.getText().toString().trim();
        String typeStr =etype.getText().toString().trim();
        if(nameStr.length()==0 || /* 任一欄位的內容為空即返回 */
                phoneStr.length()==0 ||
                emailStr.length()==0||
                typeStr.length()==0) return;

        if(v.getId()==R.id.btUpdate) {
            /* 按更新鈕 */
            update(nameStr, phoneStr, emailStr, typeStr, cur.getInt(0));
        } else {
            /* 按新增鈕 */
            addData(nameStr, phoneStr, emailStr,typeStr);
        }
        requery(); /* 更新 Cursor 內容 */
    }
    private void update(String name, String phone, String email, String type, int id) {
        ContentValues cv=new ContentValues(4);
        cv.put( FROM[0], name);
        cv.put( FROM[1], phone);
        cv.put( FROM[2], email);
        cv.put( FROM[3], type);
        /* 更新 id 所指的欄位 */
        db.update(TB_NAME, cv, "_id="+id, null);
    }

    /* 重新查詢的自訂方法 */
    private void requery() {
        cur=db.rawQuery("SELECT * FROM "+TB_NAME, null);
        /* 更改 Adapter 的 Cursor */
        adapter.changeCursor(cur);
         /* 已達上限, 停用新增鈕 */
        if(cur.getCount() == MAX) {
            btInsert.setEnabled(false);
        } else {
            btInsert.setEnabled(true);
        }
        /* 停用更新鈕 */
        btUpdate.setEnabled(false);
        /* 停用刪除鈕 */
        btDelete.setEnabled(false);
    }

    private void addData(String name, String phone, String email, String type) {
        /* 建立含 3 個欄位的 ContentValues 物件 */
        ContentValues cv = new ContentValues(4);
        cv.put( FROM[0], name);
        cv.put( FROM[1], phone);
        cv.put( FROM[2], email);
        cv.put( FROM[3], type);
        /* 新增1筆記錄 */
        db.insert(TB_NAME, null, cv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /* 移動 Cursor 至使用者選取的項目 */
        cur.moveToPosition(position);
        /* 讀出姓名,電話,Email資料並顯示 */
        etName.setText(cur.getString(
                cur.getColumnIndex(FROM[0])));
        etPhone.setText(cur.getString(
                cur.getColumnIndex(FROM[1])));
        etEmail.setText(cur.getString(
                cur.getColumnIndex(FROM[2])));
        etype.setText(cur.getString(
                cur.getColumnIndex(FROM[3])));

        btUpdate.setEnabled(true); /* 啟用更新鈕 */
        btDelete.setEnabled(true); /* 啟用刪除鈕 */
    }

    protected void Func() {

        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(ClsBtn);

        etName = (EditText)findViewById(R.id.etName);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etype  = (EditText)findViewById(R.id.etType);
        btInsert = (Button)findViewById(R.id.btInsert);
        btUpdate = (Button)findViewById(R.id.btUpdate);
        btDelete = (Button)findViewById(R.id.btDelete);
        btCall = (Button)findViewById(R.id.btCall);
        btMail = (Button)findViewById(R.id.btMail);

        /* 開啟或建立資料庫 */
        db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);

        /* 建立資料表 */
        String createTable = "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + /* 索引欄位 */
                "name VARCHAR(32), " +
                "phone VARCHAR(16), " +
                "email VARCHAR(64), "+
                "type VARCHAR(32)) ";
        db.execSQL(createTable);

        cur = db.rawQuery("SELECT * FROM "+ TB_NAME, null); /* 查詢資料 */

        /* 若查詢結果是空的則寫入測試資料 */
        if(cur.getCount() == 0){
            addData("Jhen","02-38383838","jhen@xxx.com","aaa");
            addData("Jeff","02-87878787","jeff@xxx.com","bbb");
            addData("Haoye","02-94879487","haoye@xxx.com","ccc");
            addData("Jun","02-38873887","jun@xxx.com","ddd");
        }

        adapter = new SimpleCursorAdapter(this,
                R.layout.item, cur,
                FROM,
                new int[] {R.id.name,R.id.phone,R.id.email,R.id.type}, 0);

        lv = (ListView)findViewById(R.id.lv);
        lv.setAdapter(adapter); /* 設定 Adapter */
        lv.setOnItemClickListener(this); /* 設定按下事件的監聽器 */
        requery(); /* 呼叫自訂方法, 重新查詢及設定按鈕狀態 */

        btUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onInsertUpdate(v);
                    }
                }
        );

        btDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* 刪除鈕的 On Click 事件方法 */
                        db.delete( TB_NAME, "_id=" + cur.getInt(0),null);
                        requery();
                    }
                }
        );

        btInsert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onInsertUpdate(v);
                    }
                }
        );

        btCall.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* 打電話 */
                        String uri = "tel:" + cur.getString(
                                cur.getColumnIndex(FROM[1]));
                        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(it);
                    }
                }
        );

        btMail.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* 寄送電子郵件 */
                        String uri="mailto:" + cur.getString(
                                cur.getColumnIndex(FROM[2]));
                        Intent it = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
                        startActivity(it);

                    }
                }
        );
    }
}
