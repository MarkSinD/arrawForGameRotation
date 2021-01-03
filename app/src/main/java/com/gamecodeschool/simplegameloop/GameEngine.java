package com.gamecodeschool.simplegameloop;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameEngine extends SurfaceView
        implements Runnable, EngineController{


    private ArrowSpec mArrowSpec;
    private Thread mThread = null;
    public static long mFPS = 0;

    private GameState mGameState;
    Renderer mRenderer;
    PhysicsEngine mPhysicsEngine;


    double x;
    double y;
    double DownX;
    double DownY;
    double UpX;
    double UpY;
    double DelX;
    double DelY;
    double MovingX;
    double MovingY;
    double mAngle;


    public GameEngine(Context context, Point size){
        super(context);
        mGameState = new GameState(this, context);
        mArrowSpec = new ArrowSpec(context, size);
        mRenderer = new Renderer(this, size, context, mArrowSpec);
        mPhysicsEngine = new PhysicsEngine(mArrowSpec);
    }



    @Override
    public void run() {

        while (mGameState.getThreadRunning()){
            long frameStartTime = System.currentTimeMillis();
            mPhysicsEngine.update();
            mRenderer.draw(mGameState);
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {




        int i = event.getActionIndex();
        x = (int) event.getX(i);
        y = (int) event.getY(i);

        switch (event.getAction() & MotionEvent.ACTION_MASK){

            case MotionEvent.ACTION_UP:
                UpX = x;
                UpY = y;
                Log.e("Поднял", " ");
                Log.e("UpX = ", " " + UpX);
                Log.e("UpX = ", " " + UpY);
                mArrowSpec.setIsActive(true);

                break;

            case MotionEvent.ACTION_DOWN:
                mArrowSpec.setIsActive(false);
                DownX = x;
                DownY = y;
                Log.e("---------", "-----------------");
                Log.e("Опустил", " ");
                Log.e("DownX = ", " " + DownX);
                Log.e("DownX = ", " " + DownY);
                mArrowSpec.spawn(new Point((int)DownX, (int)DownY), mAngle);

                break;

            case MotionEvent.ACTION_MOVE: // движение
                MovingX = x;
                MovingY = y;
                DelX = MovingX - DownX;
                DelY = MovingY - DownY;
                if(DelX < 0 && DelY > 0){
                    mAngle = (int)(Math.atan(-DelX / DelY) * 180/Math.PI);
                    Log.e("Work = ", " 1");
                    Log.e("Angle = ", " " + mAngle);
                }

                else if(DelX < 0 && DelY < 0) {
                    mAngle = 90 + (90 - (int) (Math.atan(-DelX / -DelY) * 180 / Math.PI));
                    Log.e("Work = ", " 2");
                    Log.e("Angle = ", " " + mAngle);
                }

                else if(DelX > 0 && DelY < 0){
                    mAngle = (int)(Math.atan(DelX / -DelY) * 180/Math.PI) + 180;
                    Log.e("Work = ", " 3");
                    Log.e("Angle = ", " " + mAngle);
                }

                else if(DelX > 0 && DelY > 0){
                    mAngle = 360 - (int)(Math.atan(-DelX/-DelY) * 180/ Math.PI);
                    Log.e("Work = ", " 4");
                    Log.e("Angle = ", " " + mAngle);
                }

                mArrowSpec.setAngle(mAngle);
                Log.e("Moving koord: ", x + " " + y);
            break;
        }
        return true;
    }

    public void stopThread(){

        mGameState.stopEverything();
        mGameState.stopThread();

        try {
            mThread.join();
        } catch (InterruptedException e){
            Log.e("Exception ", "stopThread()" + e.getMessage());
        }

    }

    public void startThread(){
        mGameState.startThread();
        mThread = new Thread(this);
        mThread.start();

    }


    @Override
    public void startNewLevel() {
        //
    }

}
