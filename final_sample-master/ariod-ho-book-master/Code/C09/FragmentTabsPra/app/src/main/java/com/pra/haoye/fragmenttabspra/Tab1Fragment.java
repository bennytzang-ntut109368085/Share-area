package com.pra.haoye.fragmenttabspra;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kancheng on 2017/12/14.
 */

public class Tab1Fragment extends Fragment {
    private String value = "";
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("=>", "Tab1Fragment onAttach");
        MainActivity mainActivity = (MainActivity)activity;
        value = "點名";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("=>", "Tab1Fragment onCreateView");
        return inflater.inflate(R.layout.frg_tab1, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("=>", "Tab1Fragment onActivityCreated");
        TextView txtResult = (TextView) this.getView().findViewById(R.id.textView1);
        txtResult.setText(value);
    }
}