package com.dreamcool.xmz.threechess;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class Chess extends AppCompatActivity {

    long s,e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        s = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        e = System.currentTimeMillis();
        Log.d("时间2：",(e-s)+"");
    }
}
