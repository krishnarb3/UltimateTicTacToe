package com.dev.pro.noob.rb.ultimatetictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by RB on 22-07-2015.
 */
public class Canvasclass extends View
{
    Rect rect1[][];
    Boolean[][] touched = new Boolean[9][9];
    Boolean timeup = false;
    Boolean[][] alreadyclicked = new Boolean[9][9];
    String[][] xoro = new String[9][9];
    Boolean[][] gridswon = new Boolean[3][3];
    String[][] wonby = new String[3][3];
    Boolean xturn = true;
    Canvas canvas;
    int count;
    Thread thread;
    Boolean[][] won = new Boolean[3][3];
    int squareclickedx;
    int squareclickedy;
    Boolean wincheck;
    int temp=-1;
    public String TAG="TAG";
    int i,j,t=0;
    Handler handler = new Handler()
    {
    Runnable runnable;

    @Override
    public void handleMessage(Message msg)
        {
        super.handleMessage(msg);
        if(msg.arg1==0)
            {
            Log.d(TAG,"Running handler");
            thread.interrupt();
            Toast.makeText(getContext(),"Your 10 secs are up",Toast.LENGTH_SHORT).show();
            timeup = true;
            }
        }
    };
    public Canvasclass(Context context)
    {
        super(context);
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                touched[i][j] = false;
                alreadyclicked[i][j] = false;
                xoro[i][j]=i+","+j;
            }

        }
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                gridswon[i][j] = false;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                wonby[i][j] = ""+i+j;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                won[i][j] = false;
    }

    public Canvasclass(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public Canvasclass(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
                touched[i][j] = false;
        }
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                gridswon[i][j] = false;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                wonby[i][j] = "";

    }



    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.canvas = canvas;

        Rect rect[][];
        ArrayList<ArrayList<Rect>> rectarray = new ArrayList<>();
        Paint paintnottouched = new Paint();
        paintnottouched.setColor(Color.GRAY);
        paintnottouched.setStyle(Paint.Style.FILL);
        Paint painttouched = new Paint();
        painttouched.setColor(Color.BLACK);
        painttouched.setStyle(Paint.Style.FILL);
        Paint painttouched1 = new Paint();
        painttouched1.setColor(Color.DKGRAY);
        painttouched1.setStyle(Paint.Style.FILL);
        Rect rect1 = new Rect();
        rect1.set(370,50,380,1030);
        canvas.drawRect(rect1,painttouched1);
        Rect rect2 = new Rect();
        rect2.set(700,50,710,1030);
        canvas.drawRect(rect2,painttouched1);
        Rect rect3 = new Rect();
        rect3.set(50,370,1030,380);
        canvas.drawRect(rect3,painttouched1);
        Rect rect4 = new Rect();
        rect4.set(50,700,1030,710);
        canvas.drawRect(rect4,painttouched1);
        rect = new Rect[9][9];
        ArrayList<Integer> xcoord = new ArrayList<>();
        ArrayList<Integer> ycoord = new ArrayList<>();
        String print = "";

        for(i=0;i<9;i++)
        {
            for(j=0;j<9;j++)
            {
                rect[i][j] = new Rect();
                if(j!=0)
                {
                    if(i!=0)
                    rect[i][j].set(10 + rect[i][j - 1].right, 10 + rect[i - 1][j].bottom, 110 + rect[i][j - 1].right, 110 + rect[i - 1][j].bottom);
                    else
                    rect[i][j].set(10 + rect[i][j-1].right,50,110+rect[i][j-1].right,150);

                }
                else
                {
                    if(i!=0)
                        rect[i][j].set(50,10+rect[i-1][j].bottom,150,110+rect[i-1][j].bottom);
                    else
                        rect[i][j].set(50,50,150,150);
                }
                //rect[j].set((50+(10*j)),(50+(10*i)),(80+(10*j)),(80+(10*i)));

                if(touched[i][j])
                {

                        Paint.FontMetrics fm = new Paint.FontMetrics();
                        paintnottouched.setTextAlign(Paint.Align.CENTER);
                        paintnottouched.setTextSize(64);
                        canvas.drawRect(rect[i][j], painttouched);
                        canvas.drawText(xoro[i][j], rect[i][j].exactCenterX(), rect[i][j].exactCenterY() + 25, paintnottouched);
                        //alreadyclicked[i][j]=true;


                        //else
                        //canvas.drawText("O", rect[i][j].exactCenterX(), rect[i][j].exactCenterY() + 25, paintnottouched);


                }
                else
                    canvas.drawRect(rect[i][j],paintnottouched);
                if(gridswon[i/3][j/3])
                {
                    alreadyclicked[i][j]=true;
                    Paint.FontMetrics fm = new Paint.FontMetrics();
                    paintnottouched.setTextAlign(Paint.Align.CENTER);
                    paintnottouched.setTextSize(64);
                    canvas.drawRect(rect[i][j], painttouched);
                    canvas.drawText(wonby[i/3][j/3], rect[i][j].exactCenterX(), rect[i][j].exactCenterY() + 25, paintnottouched);
                    if(count==0&&temp==((3*(i/3)+(j/3)+1)))
                        temp=-1;
                    count++;
                    Log.d(TAG,"temp = "+temp);
                }
                        if(temp==(3*(i/3)+(j/3)+1))
                        {
                            Paint paint = new Paint();
                            paint.setColor(Color.YELLOW);
                            paint.setStyle(Paint.Style.STROKE);
                            canvas.drawRect(rect[i][j], paint);
                        }
                    if(timeup)
                    {

                        if(temp==(3*(i/3)+(j/3)+1)&&(!alreadyclicked[i][j])&&(!touched[i][j]))
                        {
                            Paint paint = new Paint();
                            paint.setColor(Color.YELLOW);
                            Log.d(TAG, "i = " + i + " j = " + j);
                            if (xturn)
                                print = "X";
                            else
                                print = "O";
                            Log.d(TAG, print);
                            xcoord.add(i);
                            ycoord.add(j);
                        }


                        //canvas.dr
                    }
                t++;
            }
        }
        Paint pint = new Paint();
        pint.setTextAlign(Paint.Align.CENTER);
        pint.setTextSize(64);
        Random ran = new Random();
        if(xcoord.size()!=0)
        {
            int x = ran.nextInt(xcoord.size());

            Log.d(TAG, "x = " + x);
            timeup = false;
            if (!xturn)
            {
                xturn = true;
                xoro[xcoord.get(x)][ycoord.get(x)] = "O";
                touched[xcoord.get(x)][ycoord.get(x)] = true;
            } else
            {
                xturn = false;
                xoro[xcoord.get(x)][ycoord.get(x)] = "X";
                touched[xcoord.get(x)][ycoord.get(x)] = true;
            }
            canvas.drawText(print, rect[xcoord.get(x)][ycoord.get(x)].exactCenterX(), rect[xcoord.get(x)][ycoord.get(x)].exactCenterY() + 25, pint);
            squareclickedx = (xcoord.get(x) + 1) % 3;
            squareclickedy = (ycoord.get(x) + 1) % 3;
            if (squareclickedx == 0)
                squareclickedx = 3;
            if (squareclickedy == 0)
                squareclickedy = 3;
            Log.d(TAG, "Moving to square " + Integer.toString(3 * (squareclickedx - 1) + squareclickedy));
            temp = 3 * (squareclickedx - 1) + squareclickedy;
            thread.interrupt();
            thread = new Thread(new initialthread());
            thread.start();
        }
        this.rect1 = rect;
        invalidate();
    }
    public class initialthread implements Runnable
    {
        @Override
        public void run()
        {   while(!Thread.currentThread().isInterrupted())
        {
            for(int k=10;k>=0;k--)
            {
                Message message = Message.obtain();
                message.arg1=k;
                try {
                    Thread.sleep(1000);
                    //if(message.arg1==0)
                        //Toast.makeText(getContext(),"Your 10 secs are up",Toast.LENGTH_SHORT).show();
                    handler.sendMessage(message);
                    Log.d(TAG,message.arg1+" - secs");
                    } catch (InterruptedException e) {return;}

            }
        }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {   if(thread!=null)
        thread.interrupt();
        thread = new Thread(new initialthread());
        thread.start();

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:

            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    if(rect1[i][j].contains((int)x,(int)y)&&!alreadyclicked[i][j])
                    {
                        Log.d(TAG,"i = "+i+" j = "+j);
                        if(temp==-1||(temp==((3*(i/3)+(j/3)+1))))
                        {
                            squareclickedx = (i + 1) % 3;
                            squareclickedy = (j + 1) % 3;
                            if (squareclickedx == 0)
                                squareclickedx = 3;
                            if (squareclickedy == 0)
                                squareclickedy = 3;
                            Log.d(TAG, "Moving to square " + Integer.toString(3 * (squareclickedx - 1) + squareclickedy));
                            temp = 3 * (squareclickedx - 1) + squareclickedy;
                            Log.d(TAG, "Touched");
                            touched[i][j] = true;
                            if (xturn)
                            {
                                xoro[i][j] = "X";
                                xturn = false;

                            } else
                            {
                                xoro[i][j] = "O";
                                xturn = true;
                            }
                            alreadyclicked[i][j] = true;
                            if(temp==-1)
                                temp=0;
                        }

                    }
                }
            }


                    for (int i = 0; i < 3; i++)
                    {
                        if(!won[0][0])
                        if ((xoro[i][0].equals(xoro[i][1]) && xoro[i][1].equals(xoro[i][2])) || (xoro[0][i].equals(xoro[1][i]) && xoro[1][i].equals(xoro[2][i])) || (xoro[1][1].equals(xoro[2][2]) && xoro[0][0].equals(xoro[1][1])) || ((xoro[0][2].equals(xoro[1][1]) && xoro[1][1].equals(xoro[2][0]))))
                        {
                            gridswon[0][0]=true;
                            String winner;
                            Log.d(TAG,"WON");
                            if(xturn)
                            {
                                winner = "O";
                                wonby[0][0] = "O";
                            }
                            else
                            {
                                winner = "X";
                                wonby[0][0] = "X";
                            }

                            Toast.makeText(getContext(), "GRID WON BY " + winner, Toast.LENGTH_LONG).show();
                            won[0][0]=true;
                        }
                    }
                for (int i = 3; i < 6; i++)
                {
                    if(!won[1][0])
                        if ((xoro[i][0].equals(xoro[i][1]) && xoro[i][1].equals(xoro[i][2])) || (xoro[3][i-3].equals(xoro[4][i-3]) && xoro[4][i-3].equals(xoro[5][i-3])) || (xoro[4][1].equals(xoro[5][2]) && xoro[3][0].equals(xoro[4][1])) || ((xoro[3][2].equals(xoro[4][1]) && xoro[4][1].equals(xoro[5][0]))))
                    {
                        gridswon[1][0] = true;

                        String winner;
                        Log.d(TAG,"WON");
                        if(xturn)
                        {
                            winner = "O";
                            wonby[1][0] = "O";
                        }
                        else
                        {
                            winner = "X";
                            wonby[1][0] = "X";
                        }

                        Toast.makeText(getContext(), "GRID WON BY " + winner, Toast.LENGTH_LONG).show();
                        won[1][0]=true;
                    }
                }
                for (int i = 6; i < 9; i++)
                {
                    if(!won[2][0])
                        if ((xoro[i][0].equals(xoro[i][1]) && xoro[i][1].equals(xoro[i][2])) || (xoro[6][i-6].equals(xoro[7][i-6]) && xoro[7][i-6].equals(xoro[8][i-6])) || (xoro[7][1].equals(xoro[8][2]) && xoro[6][0].equals(xoro[7][1])) || ((xoro[6][2].equals(xoro[7][1]) && xoro[7][1].equals(xoro[8][0]))))
                    {
                        gridswon[2][0] = true;
                        String winner;
                        Log.d(TAG,"WON");
                        if(xturn)
                        {
                            winner = "O";
                            wonby[2][0] = "O";
                        }
                        else
                        {
                            winner = "X";
                            wonby[2][0]="X";
                        }

                        Toast.makeText(getContext(), "GRID WON BY " + winner, Toast.LENGTH_LONG).show();
                        won[2][0]=true;
                    }
                }
                for (int i = 0; i < 3; i++)
                {
                    if(!won[0][1])
                        if ((xoro[i][3].equals(xoro[i][4]) && xoro[i][4].equals(xoro[i][5])) || (xoro[0][i+3].equals(xoro[1][i+3]) && xoro[1][i+3].equals(xoro[2][i+3])) || (xoro[1][4].equals(xoro[2][5]) && xoro[0][3].equals(xoro[1][4])) || ((xoro[0][5].equals(xoro[1][4]) && xoro[1][4].equals(xoro[2][3]))))
                    {
                        gridswon[0][1] = true;
                        String winner;
                        Log.d(TAG,"WON");
                        if(xturn)
                        {
                            winner = "O";
                            wonby[0][1]="O";
                        }
                        else
                        {
                            winner = "X";
                            wonby[0][1]="X";

                        }

                        Toast.makeText(getContext(), "GRID WON BY " + winner, Toast.LENGTH_LONG).show();
                        won[0][1]=true;
                    }
                }
                for (int i = 3; i < 6; i++)
                {
                    if(!won[1][1])
                        if ((xoro[i][3].equals(xoro[i][4]) && xoro[i][4].equals(xoro[i][5])) || (xoro[3][i].equals(xoro[4][i]) && xoro[4][i].equals(xoro[5][i])) || (xoro[4][4].equals(xoro[5][5]) && xoro[3][3].equals(xoro[4][4])) || ((xoro[3][5].equals(xoro[4][4]) && xoro[4][4].equals(xoro[5][3]))))
                    {
                        gridswon[1][1] = true;
                        String winner;
                        Log.d(TAG,"WON");
                        if(xturn)
                        {
                            winner = "O";
                            wonby[1][1]="O";
                        }
                        else
                        {
                            winner = "X";
                            wonby[1][1]="X";
                        }

                        Toast.makeText(getContext(), "GRID WON BY " + winner, Toast.LENGTH_LONG).show();
                        won[1][1]=true;
                    }
                }
                for (int i = 6; i < 9; i++)
                {
                    if(!won[2][1])
                        if ((xoro[i][3].equals(xoro[i][4]) && xoro[i][4].equals(xoro[i][5])) || (xoro[6][i-3].equals(xoro[7][i-3]) && xoro[7][i-3].equals(xoro[8][i-3])) || (xoro[7][4].equals(xoro[8][5]) && xoro[6][3].equals(xoro[7][4])) || ((xoro[6][5].equals(xoro[7][4]) && xoro[7][4].equals(xoro[8][3]))))
                    {
                        gridswon[2][1] = true;
                        String winner;
                        Log.d(TAG,"WON");
                        if(xturn)
                        {
                            winner = "O";
                            wonby[2][1]="O";
                        }
                        else
                        {
                            winner = "X";
                            wonby[2][1]="X";
                        }

                        Toast.makeText(getContext(), "GRID WON BY " + winner, Toast.LENGTH_LONG).show();
                        won[2][1]=true;
                    }
                }
                for (int i = 0; i < 3; i++)
                {
                    if(!won[0][2])
                        if ((xoro[i][6].equals(xoro[i][7]) && xoro[i][7].equals(xoro[i][8])) || (xoro[0][i+6].equals(xoro[1][i+6]) && xoro[1][i+6].equals(xoro[2][i+6])) || (xoro[1][7].equals(xoro[2][8]) && xoro[0][6].equals(xoro[1][7])) || ((xoro[0][8].equals(xoro[1][7]) && xoro[1][7].equals(xoro[2][6]))))
                    {
                        gridswon[0][2] = true;
                        String winner;
                        Log.d(TAG,"WON");
                        if(xturn)
                        {
                            winner = "O";
                            wonby[0][2]="O";
                        }
                        else
                        {
                            winner = "X";
                            wonby[0][2]="X";
                        }

                        Toast.makeText(getContext(), "GRID WON BY " + winner, Toast.LENGTH_LONG).show();
                        won[0][2]=true;
                    }
                }
                for (int i = 3; i < 6; i++)
                {
                    if(!won[1][2])
                        if ((xoro[i][6].equals(xoro[i][7]) && xoro[i][7].equals(xoro[i][8])) || (xoro[3][i+3].equals(xoro[4][i+3]) && xoro[4][i+3].equals(xoro[5][i+3])) || (xoro[4][7].equals(xoro[5][8]) && xoro[3][6].equals(xoro[4][7])) || ((xoro[3][8].equals(xoro[4][7]) && xoro[4][7].equals(xoro[5][6]))))
                    {
                        gridswon[1][2] = true;
                        String winner;
                        Log.d(TAG,"WON");
                        if(xturn)
                        {
                            winner = "O";
                            wonby[1][2]="O";
                        }
                        else
                        {
                            winner = "X";
                            wonby[1][2]="X";
                        }

                        Toast.makeText(getContext(), "GRID WON BY " + winner, Toast.LENGTH_LONG).show();
                        won[1][2]=true;
                    }
                }
                for (int i = 6; i < 9; i++)
                {
                    if(!won[2][2])
                        if ((xoro[i][6].equals(xoro[i][7]) && xoro[i][7].equals(xoro[i][8])) || (xoro[6][i].equals(xoro[7][i]) && xoro[7][i].equals(xoro[8][i])) || (xoro[7][7].equals(xoro[8][8]) && xoro[6][6].equals(xoro[7][7])) || ((xoro[6][8].equals(xoro[7][7]) && xoro[7][7].equals(xoro[8][8]))))
                    {
                        gridswon[2][2] = true;
                        String winner;
                        Log.d(TAG,"WON");
                        if(xturn)
                        {
                            winner = "O";
                            wonby[2][2]="O";
                        }
                        else
                        {
                            winner = "X";
                            wonby[2][2]="X";
                        }

                        Toast.makeText(getContext(), "GRID WON BY " + winner, Toast.LENGTH_LONG).show();
                        won[2][2]=true;
                    }
                }
                for(int z=0;z<3;z++)
                if ((wonby[z][0].equals(wonby[z][1]) && wonby[z][1].equals(wonby[z][2])) || (wonby[0][z].equals(wonby[1][z]) && wonby[1][z].equals(wonby[2][z])) || (wonby[1][1].equals(wonby[2][2]) && wonby[0][0].equals(wonby[1][1])) || ((wonby[0][2].equals(wonby[1][1]) && wonby[1][1].equals(wonby[2][0]))))
                    Toast.makeText(getContext(),"Complete Match WoN by "+wonby[z][0],Toast.LENGTH_LONG).show();
                    break;
        }
        return true;
    }

}
