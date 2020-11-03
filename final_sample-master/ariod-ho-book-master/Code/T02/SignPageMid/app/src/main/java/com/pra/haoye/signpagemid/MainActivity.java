package com.pra.haoye.signpagemid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    protected Button clobtn, subtn, cleatn, mfybtn;
    protected EditText txtid, nametxt, phonetxt;
    public String temstr = "";
    private SimpleAdapter listItemAdapter;
    private ListView listView_R;
    public String IDD, NAD, PHD;
    public int IDDL, NADL, PHDL;
    public static int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private View.OnClickListener returnBtn = new View.OnClickListener() {
        public void onClick(View v) {
            MainActivity.this.finish();
        }
    };

    ArrayList<HashMap<String, Object>> listData = new ArrayList();

    protected void findViews() {
        txtid = findViewById(R.id.IdTxt);
        nametxt = findViewById(R.id.NaTxt);
        phonetxt = findViewById(R.id.phTxt);
        clobtn = findViewById(R.id.closebtn);
        subtn = findViewById(R.id.subtn);
        cleatn = findViewById(R.id.cleatn);
        listView_R = findViewById(R.id.item_route);
        mfybtn = findViewById(R.id.mfybtn);
        clobtn.setOnClickListener(returnBtn);
        cleatn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        txtid.setText("");
                        nametxt.setText("");
                        phonetxt.setText("");
                    }
                }
        );
        subtn.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        IDD = txtid.getText().toString();
                        NAD = nametxt.getText().toString();
                        PHD = phonetxt.getText().toString();
                        IDDL = txtid.length();
                        NADL = nametxt.length();
                        PHDL = phonetxt.length();
                        if (IDDL == 0 && NADL == 0 && PHDL == 0) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "資料不得為空",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else if (IDDL == 0) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "請輸入 ID",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else if (NADL == 0) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "請輸入 Name",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else if (PHDL == 0) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "請輸入 Phone",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            onSFunc(v);
                        }
                    }
                }
        );
        mfybtn.setOnClickListener(onMFunc);

        listView_R.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView lvtxt1 = view.findViewById(R.id.textView1);
                        TextView lvtxt2 = view.findViewById(R.id.textView2);
                        TextView lvtxt3 = view.findViewById(R.id.textView3);
                        Toast.makeText(MainActivity.this, lvtxt1.getText().toString(), Toast.LENGTH_SHORT).show();
                        txtid.setText(lvtxt1.getText().toString());
                        nametxt.setText(lvtxt2.getText().toString());
                        phonetxt.setText(lvtxt3.getText().toString());
                        MainActivity.position = position;
                    }
                }
        );
    }

    public View.OnClickListener onMFunc = new View.OnClickListener() {
        public void onClick(View v) {
            if (listData.size() == 0) {
                HashMap<String, Object> myHasMap = new HashMap();
                myHasMap.put("ID", IDD);
                myHasMap.put("NAME", NAD);
                myHasMap.put("PHONE", PHD);
                listData.add(myHasMap);
            } else {
                for (int i = 0; i < listData.size(); i++) {
                    HashMap<String, Object> map = listData.get(i);
                    if (map.get("ID") != null && map.get("ID").equals(IDD)) {
                        if (position != -1) {
                            View view = listView_R.getChildAt(position);
                            TextView lvtxt1 = view.findViewById(R.id.textView1);
                            TextView lvtxt2 = view.findViewById(R.id.textView2);
                            TextView lvtxt3 = view.findViewById(R.id.textView3);
                            Toast.makeText(MainActivity.this, lvtxt1.getText().toString(), Toast.LENGTH_SHORT).show();
                            lvtxt1.setText(txtid.getText().toString());
                            lvtxt2.setText(nametxt.getText().toString());
                            lvtxt3.setText(phonetxt.getText().toString());
                        }
                    }
                }
            }
        }
    };

    public void onSFunc(View v) {
        if (listData.size() == 0) {
            HashMap<String, Object> myHasMap = new HashMap();
            myHasMap.put("ID", IDD);
            myHasMap.put("NAME", NAD);
            myHasMap.put("PHONE", PHD);
            listData.add(myHasMap);
        } else {
            boolean isSave = checkMap(IDD);
            if (isSave) {
                Toast.makeText(
                        MainActivity.this,
                        "帳號已註冊",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                temstr = temstr +
                        "\n 帳號 : " + IDD +
                        "\n 姓名 : " + NAD +
                        "\n 電話 : " + PHD + "\n\n";
                Toast.makeText(
                        MainActivity.this,
                        temstr,
                        Toast.LENGTH_SHORT)
                        .show();
                HashMap<String, Object> myHasMap = new HashMap();
                myHasMap.put("ID", IDD);
                myHasMap.put("NAME", NAD);
                myHasMap.put("PHONE", PHD);
                listData.add(myHasMap);
            }
        }

        listItemAdapter = new SimpleAdapter(MainActivity.this,
                listData, /* Data Source */
                R.layout.test, /* ListItem */
                new String[]{ "ID","NAME", "PHONE"},
                new int[]{R.id.textView1, R.id.textView2, R.id.textView3}
        );

        listView_R.setAdapter(listItemAdapter);

    }

    private boolean checkMap(String ID) {
        for (HashMap<String, Object> map : listData) {
            if (map.get("ID") != null && map.get("ID").equals(IDD)) {
                return true;
            }
        }
        return false;
    }
}

