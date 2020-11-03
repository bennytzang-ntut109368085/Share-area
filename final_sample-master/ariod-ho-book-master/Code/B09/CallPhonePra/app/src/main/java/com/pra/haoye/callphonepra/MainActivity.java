package com.pra.haoye.callphonepra;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    protected Button clsbtnobj, addlinkobj, addconobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }
    protected void findViews() {
        clsbtnobj = findViewById(R.id.clsbtn);
        addlinkobj = findViewById(R.id.addlinkbtn);
        addconobj = findViewById(R.id.addconbtn);

        clsbtnobj.setOnClickListener(recls);

        addlinkobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent IntentObj = new Intent();
                        IntentObj.setAction(IntentObj.ACTION_VIEW);
                        IntentObj.setData(Uri.parse("tel:117"));
                        startActivity(IntentObj);
                    }
                }
        );

        addconobj.setOnClickListener(
                new android.view.View.OnClickListener(){
                    public void onClick(View v) {
                        Intent IntentObj = new Intent();
                        IntentObj.setAction(ContactsContract.Intents.Insert.ACTION);
                        IntentObj.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                        IntentObj.putExtra(ContactsContract.Intents.Insert.NAME, "查時");
                        IntentObj.putExtra(ContactsContract.Intents.Insert.PHONE, "117");
                        startActivity(IntentObj);
                    }
                }
        );

    }

    private View.OnClickListener recls = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.this.onDestory();
        }
    };
    private void onDestory() {
        super.onDestroy();
        System.exit(0);
    }
}
