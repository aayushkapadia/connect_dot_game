package com.example.aayushkapadia.connectdots;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by aayushkapadia on 15/4/16.
 */
public class Computer {

    public Computer() {

    }

    public int move(ArrayList<Rectangle> rect,ArrayList<Line> lines)
    {

        ArrayList<Rectangle> one=new ArrayList<>(),two=new ArrayList<>();
        ArrayList<Line> lines12 = new ArrayList<>();
        for(int i=0;i<lines.size();i++)
        {
            lines12.add(lines.get(i).clone());
        }
        for(int i=0;i<rect.size();i++){
            int temp=rect.get(i).getNoOfEdges(lines);
            Log.d("TEMP",temp+"");
            if(temp==3){
                return rect.get(i).getFreeEdge(lines);
            }
            else {

                if(temp<=1){
                    one.add(rect.get(i));
                }
                else if(temp==2)two.add(rect.get(i));
            }
        }

        Log.d("SIZE", one.size() + " " + two.size());
        Random random=new Random();
        if(one.size()!=0){
            for(int i = 0;i<two.size();i++)
            {
                Rectangle rectangle1 = two.get(i);
                lines12.set(rectangle1.getUpper(),null);
                lines12.set(rectangle1.getLower(),null);
                lines12.set(rectangle1.getLeft(),null);
                lines12.set(rectangle1.getRight(),null);

            }
            ArrayList<Integer> list1234 = new ArrayList<>();
            for(int i=0;i<one.size();i++)
            {
                Rectangle rectangle1 = one.get(i);
                if(lines12.get(rectangle1.getUpper())!=null && (!lines12.get(rectangle1.getUpper()).isClicked()))
                {
                    list1234.add(rectangle1.getUpper());
                }
                if(lines12.get(rectangle1.getLower())!=null && (!lines12.get(rectangle1.getLower()).isClicked()))
                {
                    list1234.add(rectangle1.getLower());
                }
                if(lines12.get(rectangle1.getLeft())!=null && (!lines12.get(rectangle1.getLeft()).isClicked()))
                {
                    list1234.add(rectangle1.getLeft());
                }
                if(lines12.get(rectangle1.getRight())!=null && (!lines12.get(rectangle1.getRight()).isClicked()))
                {
                    list1234.add(rectangle1.getRight());
                }
            }
            if(list1234.isEmpty())
            {
                int index1234 = random.nextInt(one.size());
                return one.get(index1234).getFreeEdge(lines);
            }
            else
            {
                int index1234 = random.nextInt(list1234.size());
                return list1234.get(index1234);
            }


        }
        else{
            if(two.isEmpty())
                return -1;
            int index=random.nextInt(two.size());
            return two.get(index).getFreeEdge(lines);
        }


    }

    public int moveHarder(ArrayList<Rectangle> rectangles,ArrayList<Line> lines)
    {
        TreeMap<Integer,ArrayList<Rectangle>> hashMap = new TreeMap<>();
        hashMap.put(0,new ArrayList<Rectangle>());
        hashMap.put(1,new ArrayList<Rectangle>());
        hashMap.put(2,new ArrayList<Rectangle>());
        hashMap.put(3,new ArrayList<Rectangle>());

        Random random = new Random();

        /* Clonning Lines Array */
        ArrayList<Line> lines12 = new ArrayList<>();
        for(int i=0;i<lines.size();i++)
        {
            lines12.add(lines.get(i).clone());
        }

        /* Clonning Lines Array */
        ArrayList<Line> lines23 = new ArrayList<>();
        for(int i=0;i<lines.size();i++)
        {
            lines23.add(lines.get(i).clone());
        }

        /* Clonning Rectangle Array */
        ArrayList<Rectangle> rectangles12 = new ArrayList<>();
        for(int i=0;i<rectangles.size();i++)
        {
            rectangles12.add(rectangles.get(i).clone());

        }


        for(int i=0;i<rectangles.size();i++){
            int temp=rectangles.get(i).getNoOfEdges(lines);
            if(temp!=4)
               hashMap.get(temp).add(rectangles.get(i));
        }

        int size3 = hashMap.get(3).size();
        if(size3!=0)
        {
            int index = random.nextInt(size3);
            return hashMap.get(3).get(index).getFreeEdge(lines);
        }

        ArrayList<Rectangle> twos = hashMap.get(2);
        for(int i=0;i<twos.size();i++)
        {
            Rectangle rectangle1 = twos.get(i);
            lines12.set(rectangle1.getUpper(),null);
            lines12.set(rectangle1.getLower(),null);
            lines12.set(rectangle1.getLeft(),null);
            lines12.set(rectangle1.getRight(),null);
        }

        ArrayList<Line> linesWithoutTwo = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for(int i=0;i<lines12.size();i++)
        {
            if(lines12.get(i)!=null && (!lines12.get(i).isClicked())) {
                linesWithoutTwo.add(lines12.get(i));
                indices.add(i);
            }
        }
        Log.d("MID","CHOSING FROM 1");
        if(linesWithoutTwo.size()!=0)
        {
            int index = random.nextInt(linesWithoutTwo.size());
            return indices.get(index);
        }

        lines12=lines23;
        Log.d("HARDER","MIN-MAX Started");
        int minOpponentsTakes = 1000000000;
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        for(int i=0;i<twos.size();i++)
        {
            Rectangle rect = twos.get(i);
            if(!lines12.get(rect.getUpper()).isClicked())
            {
                lines12.get(rect.getUpper()).setClicked(true);
                int numberOfTakes = getTakes(lines12,rectangles12);
                if(numberOfTakes<minOpponentsTakes)
                {
                    minOpponentsTakes=numberOfTakes;
                    possibleMoves.clear();
                    possibleMoves.add(rect.getUpper());
                }
                else if(numberOfTakes==minOpponentsTakes)
                {
                    possibleMoves.add(rect.getUpper());
                }
                lines12.get(rect.getUpper()).setClicked(false);
            }
            if(!lines12.get(rect.getLower()).isClicked())
            {
                lines12.get(rect.getLower()).setClicked(true);
                int numberOfTakes = getTakes(lines12,rectangles12);
                if(numberOfTakes<minOpponentsTakes)
                {
                    minOpponentsTakes=numberOfTakes;
                    possibleMoves.clear();
                    possibleMoves.add(rect.getLower());
                }
                else if(numberOfTakes==minOpponentsTakes)
                {
                    possibleMoves.add(rect.getLower());
                }
                lines12.get(rect.getLower()).setClicked(false);
            }
            if(!lines12.get(rect.getLeft()).isClicked())
            {
                lines12.get(rect.getLeft()).setClicked(true);
                int numberOfTakes = getTakes(lines12,rectangles12);
                if(numberOfTakes<minOpponentsTakes)
                {
                    minOpponentsTakes=numberOfTakes;
                    possibleMoves.clear();
                    possibleMoves.add(rect.getLeft());
                }
                else if(numberOfTakes==minOpponentsTakes)
                {
                    possibleMoves.add(rect.getLeft());
                }
                lines12.get(rect.getLeft()).setClicked(false);
            }
            if(!lines12.get(rect.getRight()).isClicked())
            {
                lines12.get(rect.getRight()).setClicked(true);
                int numberOfTakes = getTakes(lines12,rectangles12);
                if(numberOfTakes<minOpponentsTakes)
                {
                    minOpponentsTakes=numberOfTakes;
                    possibleMoves.clear();
                    possibleMoves.add(rect.getRight());
                }
                else if(numberOfTakes==minOpponentsTakes)
                {
                    possibleMoves.add(rect.getRight());
                }
                lines12.get(rect.getRight()).setClicked(false);
            }
        }
        if(possibleMoves.isEmpty())
           return -1;
        int index  = random.nextInt(possibleMoves.size());
        return possibleMoves.get(index);
    }

    public int getTakes(ArrayList<Line> lines1,ArrayList<Rectangle> rectangles1)
    {

        ArrayList<Line> lines12 = new ArrayList<>();
        for(int i=0;i<lines1.size();i++)
        {
            lines12.add(lines1.get(i).clone());
        }

        ArrayList<Rectangle> rectangles12 = new ArrayList<>();
        for(int i=0;i<rectangles1.size();i++)
        {
            rectangles12.add(rectangles1.get(i).clone());
        }

        Queue<Integer> queue1 = new LinkedList<>();
        while(true) {
            int count = 0;
            for (int i = 0; i < rectangles12.size(); i++) {
                int temp = rectangles12.get(i).getNoOfEdges(lines12);
                if (temp == 3) {
                    count++;
                    queue1.add(i);
                    lines12.get(rectangles12.get(i).getFreeEdge(lines12)).setClicked(true);
                    rectangles12.get(i).setType(2);
                }
            }
            if(count==0)
                break;
        }
        for (int i = 0; i < rectangles12.size(); i++) {
            int temp = rectangles12.get(i).getNoOfEdges(lines12);
            if (temp == 4 && rectangles12.get(i).getType()==0) {
                queue1.add(i);
            }
        }
        Log.d("RECUR",queue1.size()+"");
        return queue1.size();
    }

}
