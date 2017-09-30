package com.example.aayushkapadia.connectdots;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    public final static String EXTRA_PLAYER = "com.mycompany.myfirstapp.PLAYER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void One(View v) {

        Intent intent = new Intent(this, GridSelection.class);
        intent.putExtra(EXTRA_PLAYER, 1);
        startActivity(intent);
    }

    public void Two(View v) {
        Intent intent = new Intent(this, GridSelection.class);
        intent.putExtra(EXTRA_PLAYER, 2);
        startActivity(intent);
    }

    public void Three(View v) {
        Intent intent = new Intent(this, GridSelection.class);
        intent.putExtra(EXTRA_PLAYER, 3);
        startActivity(intent);
    }

    public void seeInstructions(View v)
    {
        Intent intent = new Intent(this,Instructions.class);
        startActivity(intent);
    }
}