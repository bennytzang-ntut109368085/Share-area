package com.pra.haoye.spinnerticket;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

    protected TextView swtv;
    protected Spinner cinema, timesa;
    protected Button sub2nobj, clsbtnobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private View.OnClickListener returnBtn = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.this.finish();
        }
    };

    protected void findViews() {

        swtv = findViewById(R.id.showtv);
        cinema = findViewById(R.id.areasper);
        timesa = findViewById(R.id.timesper);

        sub2nobj = findViewById(R.id.subbtn);

        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(returnBtn);

        sub2nobj.setOnClickListener(
                new android.view.View.OnClickListener() {
                    public void onClick(View v) {
                        String[] cinemas = getResources().getStringArray(R.array.cinemas);
                        String[] timesas = getResources().getStringArray(R.array.timesas);

                        int indexci = cinema.getSelectedItemPosition();
                        int indexti = timesa.getSelectedItemPosition();

                        swtv.setText("訂 " + cinemas[indexci] + " 票，時間為 " + timesas[indexti]);
                    }
                }
        );
    }
}
