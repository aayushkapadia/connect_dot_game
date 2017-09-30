package com.example.aayushkapadia.connectdots;

/**
 * Created by aayushkapadia on 15/4/16.
 */
public class Line {
    public static int padding=60;
    public static float radius=26;
    public static float gapX=40;
    public static float gapY=40;
    private float xStart;
    private float yStart;
    private float xEnd;
    private float yEnd;
    private boolean isHori;
    private boolean clicked;
    private int type=0;            // who has claimed the line

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public Line()
    {

    }
    public Line(float con,float start,float end,boolean hor) {
        this.isHori = hor;
        clicked=false;
        if(hor)
        {
            yStart=con;
            yEnd=con;
            xStart=start;
            xEnd=end;
        }
        else

        {
            xStart=con;
            xEnd=con;
            yStart=start;
            yEnd=end;
        }
    }

    public float getxStart() {
        return xStart;
    }



    public float getyStart() {
        return yStart;
    }



    public float getxEnd() {
        return xEnd;
    }



    public float getyEnd() {
        return yEnd;
    }


    public boolean isHori() {
        return isHori;
    }



    public boolean contains(float x,float y)
    {

        if(clicked)
            return false;
        // Log.d("OBJECT", xStart + " " + yStart + " " + isHori);
        if(isHori)
        {

            boolean val =  Math.abs(y-yStart)<=Math.min(40, gapY / 4) && x>=(xStart+radius) && x<=(xEnd-radius);
            if(val)
                clicked = true;
            return val;
        }
        else {
            boolean val =  Math.abs(x - xStart) <= Math.min(40, gapX / 4) && y >= (yStart+radius) && y <= (yEnd-radius);
            if(val)
                clicked = true;
            return val;
        }

    }

    public void set(float xStart,float yStart,float xEnd,float yEnd,boolean isHori,boolean clicked)
    {
        this.xStart=xStart;
        this.xEnd=xEnd;
        this.yStart=yStart;
        this.yEnd=yEnd;
        this.clicked=clicked;
        this.isHori=isHori;
    }

    public boolean isClicked()
    {
        return clicked;
    }

    public Line clone()
    {
        Line line = new Line();
        line.set(xStart,yStart,xEnd,yEnd,isHori,clicked);
        return line;
    }

}
