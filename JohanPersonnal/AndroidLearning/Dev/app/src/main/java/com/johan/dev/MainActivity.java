package com.johan.dev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layouttest);
    }

    public void onPressMain(View view){
        Intent intent = new Intent(this, SecondaryScreen.class);
        Log.d("Test", "this is a test");
        startActivity(intent);
    }


}
