package com.example.aayushkapadia.connectdots;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class GridSelection extends AppCompatActivity {

    public final static String EXTRA_SIZE = "com.mycompany.myfirstapp.SIZE";
    public final static String EXTRA_PLAYER = "com.mycompany.myfirstapp.PLAYER";
    public final static String DIFFICULTY = "com.example.aayushkapadia.connectdots.DIFFICULTY";
    private int noOfPlayers;
    boolean isEasy = true;
    RadioButton rad;
    LinearLayout ll ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_selection);
        ll = (LinearLayout) findViewById(R.id.diff);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent1=getIntent();
        noOfPlayers=intent1.getIntExtra(MainActivity.EXTRA_PLAYER,1);
        if(noOfPlayers!=1)
           ll.setVisibility(View.GONE);
        Log.d("Hi",noOfPlayers+"");
        if(noOfPlayers==1) {
            rad  =(RadioButton) findViewById(R.id.radio_easy);
            rad.toggle();
        }
    }

    public void grid4(View v){
        Intent intent2 = new Intent(this, GameActivity.class);// replace with game activity class

        intent2.putExtra(EXTRA_PLAYER,noOfPlayers);
        intent2.putExtra(EXTRA_SIZE,4);
        intent2.putExtra(DIFFICULTY,isEasy);
        startActivity(intent2);
    }

    public void grid6(View v){
        Intent intent2 = new Intent(this, GameActivity.class);// replace with game activity classivity.EXTRA_PLAYER,1);

        intent2.putExtra(EXTRA_PLAYER,noOfPlayers);
        intent2.putExtra(EXTRA_SIZE,6);
        intent2.putExtra(DIFFICULTY,isEasy);
        startActivity(intent2);
    }

    public void grid8(View v){
        Intent intent2 = new Intent(this, GameActivity.class);// replace with game activity class

        intent2.putExtra(EXTRA_PLAYER,noOfPlayers);
        intent2.putExtra(EXTRA_SIZE,8);
        intent2.putExtra(DIFFICULTY,isEasy);
        startActivity(intent2);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_easy:
                if (checked)
                    isEasy=true;
                    break;
            case R.id.radio_hard:
                if (checked)
                    isEasy = false;
                    break;
        }
    }
}
