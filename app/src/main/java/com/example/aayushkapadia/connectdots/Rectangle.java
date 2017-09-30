package com.example.aayushkapadia.connectdots;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by aayushkapadia on 15/4/16.
 */
public class Rectangle {
    private float upperX;
    private float upperY;
    private float lowerX;
    private float lowerY;
    private int type;      // type is 0 for nothing 1 for user and 2 for computer 3 for user2 4 for user3
    private int upper;
    private int lower;
    private int left;
    private int right;

    public Rectangle(float upperX, float upperY, float lowerX, float lowerY) {
        this.upperX = upperX;
        this.upperY = upperY;
        this.lowerX = lowerX;
        this.lowerY = lowerY;
        this.type=0;
    }

    public float getUpperX() {
        return upperX;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getUpperY() {
        return upperY;
    }

    public float getLowerX() {
        return lowerX;
    }

    public float getLowerY() {
        return lowerY;
    }

    public int getType() {
        return type;
    }

    public void setLines(int upper,int lower,int left,int right)
    {
        this.upper=upper;
        this.left=left;
        this.lower=lower;
        this.right=right;
    }

    public int getUpper() {
        return upper;
    }

    public int getLower() {
        return lower;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getNoOfEdges(ArrayList<Line> lines)
    {
        int answer = 0;
        if(lines.get(upper).isClicked())
            answer++;
        if(lines.get(lower).isClicked())
            answer++;
        if(lines.get(left).isClicked())
            answer++;
        if(lines.get(right).isClicked())
            answer++;
        return answer;
    }

    public int getFreeEdge(ArrayList<Line> lines)
    {
        ArrayList<Integer> possible = new ArrayList<>();
        int[] arr = new int[2];
        if(!lines.get(upper).isClicked())
        {
            possible.add(0);
        }
        if(!lines.get(lower).isClicked()) {
           possible.add(1);
        }
        if(!lines.get(left).isClicked())
        {
            possible.add(2);
        }
        if(!lines.get(right).isClicked())
        {
            possible.add(3);
        }
        if(possible.isEmpty())
            return -1;
        Random random = new Random();
        int index = random.nextInt(possible.size());
        int value = possible.get(index);
        if(value==0)
            return upper;
        else if(value==1)
            return lower;
        else if(value==2)
            return left;
        else
            return right;

    }

    public Rectangle clone()
    {
           Rectangle rectangle = new Rectangle(upperX,upperY,lowerX,lowerY);
           rectangle.setType(type);
           rectangle.setLines(upper,lower,left,right);
           return  rectangle;
    }
}
