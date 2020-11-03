package com.gcm.haoye.goodclassmate;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class MainActivity extends ListActivity {
    public static final int REQUESTCODE = 123;
    private static final String BUNDLEID = "commit";
    protected Button insertbtnobj, clsbtnobj;
    private Context context;
    private CommentsDataSource datasource;
    private MyAdapter adapter;

    /* SD */
    protected Button btnWriteSDFile, btnReadSDFile, btnClearScreen;
    private final String SD_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public static final String FILE_PATH = "/fileio";
    private final String INPUT_FILENAME = "file.xml";

    String dirPath = SD_PATH+FILE_PATH+"/";
    String filenameWithPath = SD_PATH+FILE_PATH+"/"+INPUT_FILENAME;

    protected EditText txtData;

    /* SQLiteCount */
    protected Button btnSQLiteCount;
    protected CommentsDataSource dbcds;
    protected String sqlgc, sqlgcmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        findViews();
        setReqOrientationFunc();
        MyHandler myHandler = new MyHandler(datasource);
        Thread t = new Thread(myHandler);
        t.start();
        setSDListener();
        SQLiteCount();
    }

    protected void SQLiteCount() {
        btnSQLiteCount = findViewById(R.id.btnSQLiteCount);
        btnSQLiteCount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbcds = new CommentsDataSource(context);
                        dbcds.open();
                        sqlgc = Integer.toString(dbcds.getSQLiteCount());
                        dbcds.close();
                        sqlgcmsg = "SQLite 共有 " + sqlgc + " 筆資料.";
                        txtData.setText(sqlgcmsg);
                    }
                }
        );
    }
    protected void setSDListener() {

        /* SD */
        txtData = findViewById(R.id.txtData);
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
        int permsRequestCode = 200;
        requestPermissions(perms, permsRequestCode);

        File folder = new File(Environment.getExternalStorageDirectory() + FILE_PATH );
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            // Do something on success
            Log.i("jpyuMsg", " create fileio successfully");
        } else {
            // Do something else on failure
            Log.i("jpyuMsg", Environment.getExternalStorageDirectory() + FILE_PATH);
            Log.i("jpyuMsg", "failed to create fileio ");
        }

        btnWriteSDFile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeFileToSdcardv2();
            } // onClick
        }); // btnWriteSDFile

        btnReadSDFile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                readFileFromSdcardv1();
            } // onClick
        }); // btnReadSDFile

        btnClearScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // clear text box
                txtData.setText("");
            }
        });
    }

    private View.OnClickListener ClsBtn = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.this.onDestory();
        }
    };
    private View.OnClickListener InsertBtn = new View.OnClickListener(){
        public void onClick(View v){
            Intent IntentObj = new Intent();
            IntentObj.setClass(MainActivity.this, Main2Activity.class);
            MainActivity.this.startActivityForResult(IntentObj,REQUESTCODE);

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE){
            adapter.list = datasource.getAllComments();
            adapter.notifyDataSetChanged();
        }
    }

    protected void setReqOrientationFunc() {
        /* 設定螢幕不隨手機旋轉 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        /* 設定螢幕直向顯示 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void findViews() {
        this.context = this;
        insertbtnobj = findViewById(R.id.insertbtn);
        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(ClsBtn);
        insertbtnobj.setOnClickListener(InsertBtn);
        datasource = new CommentsDataSource(this);
        datasource.open();
        List<Comment> values = datasource.getAllComments();
        if(values != null) {
        /* use the SimpleCursorAdapter to show the elements in a ListView */
            adapter = new MyAdapter(context,values);

            setListAdapter(adapter);
        }

        btnWriteSDFile = (Button) findViewById(R.id.btnWriteSDFile);
        btnReadSDFile = (Button) findViewById(R.id.btnReadSDFile);
        btnClearScreen = (Button) findViewById(R.id.btnClearScreen);
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void onDestory() {
        super.onDestroy();
        System.exit(0);
    }

    void writeFileToSdcardv2(){
        /* write on SD card file data in the text box */
        try {
            /* create a File object for the parent directory */
            File newDir = new File(dirPath);
            /* have the object build the directory structure, if needed. */
            newDir.mkdirs();
            File myFile = new File(filenameWithPath);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append(txtData.getText());
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getBaseContext(),
                    "Done writing SD 'mysdfile.txt'",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(
                    getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    void readFileFromSdcardv1(){
        try {
            File myFile = new File(filenameWithPath);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
            txtData.setText(aBuffer);
            myReader.close();
            Toast.makeText(getBaseContext(),
                    "Done reading SD 'mysdfile.txt'",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(),
                    e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    class MyHandler extends Thread {
        Handler handler = new Handler();
        CommentsDataSource datasource;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                datasource.open();
                List<Comment> values = datasource.getAllComments();
                if(values != null) {
                    System.out.println(values);
                    for(int i = 0; i < values.size(); i++){
                        int DataEndNum = Integer.parseInt(values.get(i).getDataend());
                        int WedtNum = Integer.parseInt(values.get(i).getWedt());
                        int limitday = DataEndNum - WedtNum;
                        long DateStartNum = Long.parseLong(values.get(i).getDatestart());
                        SimpleDateFormat sdf = new SimpleDateFormat("dd");
                        Date limitdate = null;
                        try {
                            limitdate = sdf.parse(limitday+"");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date date = new Date();
                        date.setTime(DateStartNum);
                        date.after(limitdate);
                        Calendar c = Calendar.getInstance();
                        if(c.getTime().getTime()>date.getTime()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context,"有筆金額還款時間已到",Toast.LENGTH_LONG).show();
                                }
                            });
                        };

                    }
                }
                handler.postDelayed(this,10000);
            }
        };

        public MyHandler(CommentsDataSource datasource) {
            this.datasource = datasource;
        }

        @Override
        public void run() {

            runOnUiThread(runnable);
        }

    }


}
