package com.pra.haoye.sdcardpra;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    // GUI controls
    EditText txtData;
    Button btnWriteSDFile;
    Button btnReadSDFile;
    Button btnClearScreen;
    Button btnClose;

    private final String SD_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public static final String FILE_PATH = "/fileio";
    private final String INPUT_FILENAME = "file.xml";

    String dirPath = SD_PATH+FILE_PATH+"/";
    String filenameWithPath = SD_PATH+FILE_PATH+"/"+INPUT_FILENAME;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
        int permsRequestCode = 200;
        requestPermissions(perms, permsRequestCode);

        findViews();
        setListener();

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
    }// onCreate

    private void findViews() {

        // bind GUI elements with local controls
        txtData = (EditText) findViewById(R.id.txtData);
        txtData.setHint("Enter some lines of data here...");
        btnWriteSDFile = (Button) findViewById(R.id.btnWriteSDFile);
        btnReadSDFile = (Button) findViewById(R.id.btnReadSDFile);
        btnClearScreen = (Button) findViewById(R.id.btnClearScreen);
        btnClose = (Button) findViewById(R.id.btnClose);
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

    private void setListener() {

        btnWriteSDFile.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                writeFileToSdcardv2();
            }// onClick
        }); // btnWriteSDFile

        btnReadSDFile.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                readFileFromSdcardv1();
            }// onClick
        }); // btnReadSDFile

        btnClearScreen.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // clear text box
                txtData.setText("");
            }
        }); // btnClearScreen

        btnClose.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // clear text box
                finish();
            }
        }); // btnClose
    }


}