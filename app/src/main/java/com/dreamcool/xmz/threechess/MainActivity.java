package com.dreamcool.xmz.threechess;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBnt;
    private Button exittBnt;
    long s,e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        try{
            reportFullyDrawn();
        }catch(SecurityException e){
        }

        startBnt = findViewById(R.id.start_bnt);
        exittBnt = findViewById(R.id.exit_bnt);
        startBnt.setOnClickListener(this);
        exittBnt.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_bnt:
                s = System.currentTimeMillis();
                Intent intent = new Intent(MainActivity.this ,Chess.class);
                startActivity(intent);
                e = System.currentTimeMillis();
                Log.d("时间：",(e-s)+"");
                break;
            case R.id.exit_bnt:
                finish();
                break;

        }
    }
}
