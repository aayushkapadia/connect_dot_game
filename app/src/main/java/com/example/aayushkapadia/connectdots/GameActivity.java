package com.example.aayushkapadia.connectdots;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    LineView drawLine;
    LinearLayout ll, ll2,ll3;
    TextView scoreView,currentTurn;
    Button undoButton;
    int n1, noOfPlayers1;
    boolean isEasy=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ll = (LinearLayout) findViewById(R.id.beta);
        ll2 = (LinearLayout) findViewById(R.id.alpha);
        ll3 = (LinearLayout) findViewById(R.id.score_undo_layout);
        undoButton = (Button) findViewById(R.id.undo_button);
        scoreView = (TextView) findViewById(R.id.score_view);
        currentTurn = (TextView) findViewById(R.id.current_move);
       // scoreView.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        noOfPlayers1 = getIntent().getIntExtra(GridSelection.EXTRA_PLAYER, 1);
        n1 = getIntent().getIntExtra("com.mycompany.myfirstapp.SIZE", 4);
        isEasy = getIntent().getBooleanExtra(GridSelection.DIFFICULTY,true);
        drawLine = new LineView(this);
       /* if(noOfPlayers1!=1)
        {
            undoButton.setEnabled(false);
            undoButton.setVisibility(View.INVISIBLE);
        }*/
        ll2.setBackgroundColor(Color.WHITE);
        //scoreView.setBackgroundColor(Color.BLACK);

        /*int[] array = new int[2];
        ll.getLocationOnScreen(array);
            Log.d("HELLO", "array  X = " + array[0] + ", Y = " + array[1]);*/
        //drawLine.setBackgroundColor(Color.BLACK);
        ll.addView(drawLine, layoutParams);
        drawLine.setParam(n1, noOfPlayers1, scoreView,ll3,currentTurn,isEasy);

        /*int[] location = new int[2];
        ll.getLocationOnScreen(location);
        Log.v("HELLO", "left: " + location[0]);
        Log.v("HELLO", "top: " + location[1]);*/

        // Log.d("HELLO", (ll.getBottom() - ll.getTop())+"");


    }

    public void onUndo(View view)
    {
           drawLine.undo();
    }
}
