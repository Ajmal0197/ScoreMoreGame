package com.example.scoremoregame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View
{
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];
    private Paint paintBackground;
    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth, canvasHeight;

    private Boolean touch = false;

    private int yellowX, yellowY, yellowSpeed = 16; //balls attributes
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 22;
    private Paint redPaint = new Paint();

    private int score, lifeCounterOfFish;

    public FlyingFishView(Context context)
    {
        super(context);

        fish[0]= BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0]= BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1]= BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounterOfFish = 3;

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Rect dest = new Rect(0, 0, getWidth(), getHeight());
        paintBackground = new Paint();
        paintBackground.setFilterBitmap(true);
        canvas.drawBitmap(backgroundImage, null, dest, paintBackground);  //puts the background covering whole area


        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        //logic to fly fish on tap
        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight()*3;
        fishY = fishY + fishSpeed;

        if(fishY < minFishY)
        {
            fishY = minFishY;
        }
        if(fishY > maxFishY)
        {
            fishY = maxFishY;
        }

        fishSpeed = fishSpeed + 2;

        if(touch)
        {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }


        //balls logic
        yellowX = yellowX - yellowSpeed;
        if(hitBallChecker(yellowX, yellowY))
        {
            score =score+10;
            yellowX = -100;
        }
        if(yellowX < 0)
        {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX, yellowY, 30, yellowPaint);



        greenX = greenX - greenSpeed;
        if(hitBallChecker(greenX, greenY))
        {
            score =score+20;
            greenX = -100;
        }
        if(greenX < 0)
        {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX, greenY, 25, greenPaint);



        redX = redX - redSpeed;
        if(hitBallChecker(redX, redY))
        {
            redX = -100;
            lifeCounterOfFish--;

            if(lifeCounterOfFish == 0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);
            }
        }
        if(redX < 0)
        {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(redX, redY, 35, redPaint);


        canvas.drawText("Score : " + score, 20, 60, scorePaint);


        for (int i=0; i<3; i++)
        {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if(i<lifeCounterOfFish)
            {
                canvas.drawBitmap(life[0], x, y, null);
            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);

            }
        }


//        canvas.drawBitmap(life[0], 580, 10, null);
//        canvas.drawBitmap(life[0], 680, 10, null);
//        canvas.drawBitmap(life[0], 780, 10, null);

    }



    public Boolean hitBallChecker(int x, int y)
    {
        if(fishX<x && x<(fishX + fish[0].getWidth()) && fishY<y && y<(fishY + fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
         if(event.getAction() == MotionEvent.ACTION_DOWN)
         {
             touch = true;
             fishSpeed = -25;  //speed of fish
         }
         return true;
    }
}
