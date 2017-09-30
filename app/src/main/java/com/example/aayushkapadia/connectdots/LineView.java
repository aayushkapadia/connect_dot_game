package com.example.aayushkapadia.connectdots;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by aayushkapadia on 14/4/16.
 */
public class LineView extends View {

    private Paint paint = new Paint();     // for lines before touch
    private Paint paintCircle = new Paint();  // for dots
    private Paint paintAfterTouch = new Paint();  // for lines after touch
    private Paint paintPrevious = new Paint();
    private Paint paintColorUser = new Paint();    // for colouring the rectangle covered by user
    private Paint paintColorComputer = new Paint();  // for colouring the rectangle covered by computer
    private Paint paintColorUser2 = new Paint();
    private Paint paintColorUser3 = new Paint();
    private Paint paintColorUser4 = new Paint();
    private float height;         // height of window
    private float width;         // width of window
    private int n;         // n*(n-1) grid
    private ArrayList<Line> lines;     // collection of lines
    private ArrayList<com.example.aayushkapadia.connectdots.Rectangle> rectangles;  // collection of rectangles
    private boolean isAIOn;
    private int[] colors = {Color.parseColor("#ffd600"),Color.GREEN,Color.CYAN};
    private int noOfPlayers;
    private int turn;
    private int[] score = new int[3];
    private Computer computer = null;
    private TextView scoreView;
    private TextView currentTurn;
    private boolean setted = false;
    private int previousIndex=-1;
    private int padding=60;
    private LinearLayout ll;
    private Stack<Line> play;   // consist of lines that ihas been clicked by either the user or computer
    private boolean gameOver=false;
    private boolean isEasy = true;


    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context,AttributeSet attr)
    {
        super(context,attr);
    }


    public void setParam(int n,int noOfPlayers,TextView scoreView,LinearLayout ll,TextView currentTurn,boolean isEasy)
    {
        this.isEasy=isEasy;
        this.currentTurn=currentTurn;
        play = new Stack<>();
        this.ll = ll;
        this.n = n;
        this.scoreView=scoreView;
        score[0]=0;
        score[1]=0;
        score[2]=0;
        this.noOfPlayers=noOfPlayers;
        if(noOfPlayers==1)
        {
            isAIOn = true;
            this.noOfPlayers++;
            this.turn = 1;
            computer = new Computer();
            currentTurn.setText("Your move");
        }
        else {
            isAIOn = false;
            this.turn=0;
            currentTurn.setText("Player 1 move");
        }

        lines = new ArrayList<>();
        rectangles = new ArrayList<>();

        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(25);

        paintCircle.setColor(Color.parseColor("#5d4037"));

        paintAfterTouch.setColor(Color.BLUE);
        paintAfterTouch.setStrokeWidth(25);

        paintPrevious.setColor(Color.parseColor("#0277bd"));
        paintPrevious.setStrokeWidth(25);


        if(isAIOn)
        {
            paintColorComputer.setColor(colors[0]);
            paintColorUser.setColor(colors[1]);
        }
        else
        {
            paintColorUser.setColor(colors[0]);
            paintColorUser2.setColor(colors[1]);
            paintColorUser3.setColor(colors[2]);
        }
        paintColorUser4.setColor(Color.WHITE);



    }

    @Override
    public void onDraw(Canvas canvas) {


        //RectF bounds = new RectF(canvas.getClipBounds());
       // float height1 = bounds.height();
       // float width1 = bounds.width();
        float height1 = getHeight();
        float width1 = getWidth();
        setHeightAndWidth(height1, width1);
        Log.d("DRAW", height + " " + width);
        super.onDraw(canvas);
        float startXPoint = padding;
        float lastXPoint = width - padding;
        float startYPoint = padding;
        float lastYPoint = height - padding;
        float gapX = (lastXPoint - startXPoint)/(n-1);
        float gapY = (lastYPoint - startYPoint)/n;


        //rectangles.get(0).setType(1);
        //rectangles.get(1).setType(2);

        // coloring the rectangles
        for(int i=0;i<rectangles.size();i++)
        {
            com.example.aayushkapadia.connectdots.Rectangle rect = rectangles.get(i);
            if(rect.getType()==1)
            {
                canvas.drawRect(rect.getUpperX(),rect.getUpperY(),rect.getLowerX(),rect.getLowerY(),paintColorUser);
            }
            else if(rect.getType()==2)
            {
                canvas.drawRect(rect.getUpperX(),rect.getUpperY(),rect.getLowerX(),rect.getLowerY(),paintColorComputer);
            }
            else if(rect.getType()==3)
            {
                canvas.drawRect(rect.getUpperX(),rect.getUpperY(),rect.getLowerX(),rect.getLowerY(),paintColorUser2);
            }
            else if(rect.getType()==4)
            {
                canvas.drawRect(rect.getUpperX(),rect.getUpperY(),rect.getLowerX(),rect.getLowerY(),paintColorUser3);
            }
            else
            {
                canvas.drawRect(rect.getUpperX(),rect.getUpperY(),rect.getLowerX(),rect.getLowerY(),paintColorUser4);
            }
        }

        /* Drawing Lines */
        for(int i=0;i<lines.size();i++)
        {
            Line line = lines.get(i);
            if(line.isClicked()) {
                if(previousIndex==i)
                    canvas.drawLine(line.getxStart(), line.getyStart(), line.getxEnd(), line.getyEnd(), paintPrevious);
                else
                    canvas.drawLine(line.getxStart(), line.getyStart(), line.getxEnd(), line.getyEnd(), paintAfterTouch);
            }
            else
                canvas.drawLine(line.getxStart(),line.getyStart(),line.getxEnd(),line.getyEnd(),paint);
        }

        /* drawing Dots */
        for(int j=0;j<n;j++) {
            float startingX = startXPoint + (gapX * j);
            canvas.drawCircle(startingX, startYPoint, (float)(15/1080.00)*width, paintCircle);
            for (int i = 0; i < n; i++) {
                float startingY = startYPoint + (gapY * i);
                float endingY = startingY + gapY;
                canvas.drawCircle(startingX, endingY, (float)(15 / 1080.00)*width, paintCircle);
            }

        }
        previousIndex = -1;
        showScore();
        if((score[0]+score[1]+score[2])==(n*(n-1)))
        {
            gameOver = true;
            showWinner();
        }
    }

    /**
     * Detects the line touched by user
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(gameOver)
            return true;
        float touchX = event.getX();
        float touchY = event.getY();
        int myIndex = -1;

        if(isAIOn && turn==0)
        {
            if(isEasy)
                myIndex = computer.move(rectangles,lines);
            else
                myIndex =  computer.moveHarder(rectangles,lines);
            if(myIndex!=-1) {
                lines.get(myIndex).setClicked(true);
                lines.get(myIndex).setType(2);               // 2 for computer
                previousIndex = myIndex;
            }
            Log.d("COMP INDEX", myIndex + "");
        }
        else {



        /* deciding which line is selected */
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(touchX, touchY)) {
                    myIndex = i;
                    break;
                }
            }
            Log.d("USER's MOVE",myIndex+"");
        }

        /* Error Handling */
        if(myIndex==-1 && isAIOn) {
            turn = 1;
            return true;
        }

        if(myIndex==-1)
            return true;

        play.push(lines.get(myIndex));
        int type;
        if(isAIOn)
        {
            if(turn==0)
                type = 2;
            else
                type = 1;
        }
        else
        {
            if(turn==0)
                type = 1;
            else if(turn==1)
                type = 3;
            else
                type = 4;
        }

        if(type!=2)
        {
            lines.get(myIndex).setType(type);
        }
        boolean value = detectRectangles(type,turn);
        if(value)
            Log.d("VAL","TRUE");
        else
            Log.d("VAL", "FALSE");
        invalidate();    // updating the picture
        if(!value)
        {
            turn = (turn + 1)%noOfPlayers;
        }
        if(isAIOn && turn==0)
        {
            return onTouchEvent(event);
        }
        else
            return true;
    }


    /**
     * This is just for setting up the objects and Arraylist needed for the game.
     * @param height1
     * @param width1
     */

    public void setHeightAndWidth(float height1,float width1)
    {
        if(setted)
            return;
        setted = true;
        this.height=height1;
       // this.height = height1 - ll.getHeight();
        this.width = width1;
        padding = (int)((padding/1080.00)*width);
        Line.padding=padding;
        Line.radius=(float)(15/1080.00)*width+1;
        float strokeWidth = (float)((25/1080.00)*width);
        paintPrevious.setStrokeWidth(strokeWidth);
        paintAfterTouch.setStrokeWidth(strokeWidth);
        paint.setStrokeWidth(strokeWidth);
        float startXPoint = padding;
        float lastXPoint = width - padding;
        float startYPoint = padding;
        float lastYPoint = height - padding;
        float gapX = (lastXPoint - startXPoint)/(n-1);
        float gapY = (lastYPoint - startYPoint)/n;
        Line.gapX = gapX ;
        Line.gapY=gapY;

        /* Setting up Rectangles Arraylist */
        for(int i=0;i<(n-1);i++)
        {
            float startingX = startXPoint + (gapX * i);
            for(int j=0;j<n;j++)
            {
                float startingY = startYPoint + (gapY*j);
                com.example.aayushkapadia.connectdots.Rectangle rect = new com.example.aayushkapadia.connectdots.Rectangle(startingX,startingY,startingX+gapX,startingY+gapY);
                rect.setLines(n*n + i*(n+1) + j,n*n + i*(n+1) + j + 1,i*n + j,(i+1)*n + j);
                rectangles.add(rect);
            }
        }

        /* Setting up Lines ArrayList */
        ArrayList<Line> temp = new ArrayList<>();
        for(int j=0;j<n;j++) {
            float startingX = startXPoint + (gapX * j);
            for (int i = 0; i < n; i++) {
                float startingY = startYPoint + (gapY * i);
                float endingY = startingY + gapY;
                Line line = new Line(startingX,startingY,endingY,false);
                lines.add(line);
                if(j!=0)
                {

                    Line line1 = new Line(startingY,startingX-gapX,startingX,true);
                    temp.add(line1);
                }
            }
            if(j!=0)
            {
                Line line1 = new Line(lastYPoint,startingX-gapX,startingX,true);
                temp.add(line1);
            }
        }
        lines.addAll(temp);
    }

    /**
     * Detect if any rectangle has been made by either the user or computer and if made return true
     * Type and turn is the same thing just notifies us about who has claimed the rectangle.
     * @param type
     * @param turn
     * @return
     */

    private boolean detectRectangles(int type,int turn)
    {
        boolean value = false;
        for(int i=0;i<rectangles.size();i++)
        {
            com.example.aayushkapadia.connectdots.Rectangle rectangle = rectangles.get(i);
            if(rectangle.getType()==0) {
                boolean upper,lower,left,right;
                upper = lines.get(rectangle.getUpper()).isClicked();
                lower = lines.get(rectangle.getLower()).isClicked();
                left = lines.get(rectangle.getLeft()).isClicked();
                right = lines.get(rectangle.getRight()).isClicked();
                if(upper && lower && left && right)
                {
                    rectangle.setType(type);
                    score[turn]++;
                    value = true;
                }
            }
        }
        return value;
    }

    /**
     *   Used for displaying score on scoreView
     */

    private void showScore()
    {

        scoreView.setText("");
        if(isAIOn)
        {
            String str123="User = "+score[1]+" ";
            Spannable word = new SpannableString(str123);
            word.setSpan(new ForegroundColorSpan(colors[1]), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            scoreView.append(word);
            str123="Computer = "+score[0]+" ";
            word = new SpannableString(str123);
            word.setSpan(new ForegroundColorSpan(colors[0]), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            scoreView.append(word);
            if(turn==0)
                currentTurn.setText("Computer Turn");
            else
                currentTurn.setText("Your Turn");

        }
        else
        {

            for(int i=0;i<noOfPlayers;i++)
            {
                String str123="User"+(i+1)+"="+score[i]+" ";
                Spannable word = new SpannableString(str123);
                word.setSpan(new ForegroundColorSpan(colors[i]), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                scoreView.append(word);
            }
            if(turn==0)
                currentTurn.setText("Player 1 Turn");
            else if(turn==1)
                currentTurn.setText("Player 2 Turn");
            else
                currentTurn.setText("Player 3 Turn");
        }

    }

    /**
     *    Used for declaring winner of the game
     */

    private void showWinner()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Result");
        String str;
        if(isAIOn)
        {
            if(score[0]>score[1])
            {
                str="Computer Wins";
            }
            else if(score[0]==score[1])
            {
                str="It's a Draw";
            }
            else
            {
                str="Great, You won!";
            }
        }
        else
        {
            if(score[0]>score[1])
            {
                 if(score[2]>score[0])
                 {
                     str="Great Game Player 3 Wins";
                 }
                else if(score[2]==score[0])
                 {
                     str="Great Game Draw between Player 1 and Player 3";
                 }
                else
                 {
                     str="Great Game Player 1 Wins";
                 }
            }
            else if(score[0]==score[1])
            {
                if(score[2]>score[0])
                {
                    str="Great Game Player 3 Wins";
                }
                else if(score[2]==score[0])
                {
                   str="Great Game Its a draw";
                }
                else
                {
                    str="Great Game Draw between Player 1 and Player 2";
                }
            }
            else
            {
                if(score[2]>score[1])
                {
                   str="Great Game Player 3 Wins";
                }
                else if(score[2]==score[1])
                {
                    str="Great Game Draw between Player 2 and Player 3";
                }
                else
                {
                    str="Great Game Player 2 Wins";
                }
            }



        }
        alert.setMessage(str);
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    /**
     *    Undo operation . Activated when undo button clicked
     */

    public void undo()
    {
         gameOver=false;
         if(play.empty())
             return;
         if(play.peek().getType()!=2)
         {
             int type = play.peek().getType();
             if(type==1) {
                 if(isAIOn)
                    turn = 1;
                 else
                     turn=0;
             }
             else if(type==3)
                 turn=1;
             else
                 turn=2;
             play.peek().setType(0);
             play.peek().setClicked(false);
             removeRectangles();
             play.pop();
             invalidate();
             return;
         }
         else
         {
             play.peek().setType(0);
             play.peek().setClicked(false);
             removeRectangles();
             play.pop();
             undo();
         }
    }

    public void removeRectangles()
    {
        for(int i=0;i<rectangles.size();i++)
        {
            Rectangle rectangle = rectangles.get(i);
            if(rectangle.getType()!=0) {
                boolean upper,lower,left,right;
                upper = lines.get(rectangle.getUpper()).isClicked();
                lower = lines.get(rectangle.getLower()).isClicked();
                left = lines.get(rectangle.getLeft()).isClicked();
                right = lines.get(rectangle.getRight()).isClicked();
                if(!(upper && lower && left && right))
                {
                    int type = rectangle.getType();
                    if(type==2)
                        score[0]--;
                    else if(type==3)
                        score[1]--;
                    else if(type==4)
                        score[2]--;
                    else
                    {
                        if(isAIOn)
                            score[1]--;
                        else
                            score[0]--;
                    }

                    rectangle.setType(0);
                }
            }
        }
    }

}
