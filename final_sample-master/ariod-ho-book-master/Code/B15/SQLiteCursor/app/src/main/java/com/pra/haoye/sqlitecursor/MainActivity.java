package com.pra.haoye.sqlitecursor;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    /* 資料庫名稱 */
    protected static final String db_name = "dbname";

    /* 資料表名稱 */
    protected static final String tb_name = "tbname";
    protected SQLiteDatabase db; /* 資料庫 */
    protected TextView showtvobj;
    protected Button clsbtnobj;
    protected String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        funcActivity();
        clsFunc();
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

    protected void clsFunc() {
        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(ClsBtn);
    }

    protected void funcActivity() {
        showtvobj = (TextView)findViewById(R.id.showtv);

        /* 開啟或建立資料庫 */
        db = openOrCreateDatabase(
                db_name,
                Context.MODE_PRIVATE,
                null
        );

        String createTable="CREATE TABLE IF NOT EXISTS " +
                tb_name + /* 資料表名稱 */
                "(" +
                 /* 姓名欄位 */
                "name VARCHAR(32), " +
                 /* 電話欄位 */
                "phone VARCHAR(16), " +
                /* ID 欄位 */
                "id VARCHAR(64)" +
                ")";
        db.execSQL(createTable); /* 建立資料表 */

        /* 查詢 tb_name 資料表中的所有資料 */
        Cursor c = db.rawQuery("SELECT * FROM " + tb_name, null);
        if (c.getCount() == 0){	/* 若無資料, 則立即新增 2筆資料 */
            /* 插入 2 筆資料 */
            addData( "Jhen", "093838", "D001");
            addData( "Jeff", "099487", "D002");
            /* 重新查詢 */
            c = db.rawQuery("SELECT * FROM "+tb_name, null);
        }
        /* 取得及顯示資料庫資訊 */
        str += "取得及顯示資料庫資訊 \n\n 資料庫檔路徑 : " + db.getPath() + "\n\n" +
                "資料庫分頁大小 : " + db.getPageSize() + " Bytes\n\n" +
                "資料庫大小上限 : " + db.getMaximumSize() + " Bytes\n\n";

        if (c.moveToFirst()){ /* 移到第 1 筆資料 (若有資料才繼續) */
            str = "總共有 "+c.getCount()+ "筆資料\n";
            str += "### \n";

            do{ /* 逐筆讀出資料 */
                str += "name : " + c.getString(0) + "\n";
                str += "phone : " + c.getString(1) + "\n";
                str += "id : " + c.getString(2) + "\n";
                str += "### \n";
            } while(c.moveToNext()); /* 有一下筆就繼續迴圈 */

            showtvobj.setText(str);
        }
        db.close(); /* 關閉資料庫 */

    }

    private void addData(String name, String phone, String id) {
        /* 建立含 3 個資料項目的物件 */
        ContentValues cv = new ContentValues(3);
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("id", id);
        db.insert(tb_name, null, cv); /* 將資料加到資料表 */
    }
}
