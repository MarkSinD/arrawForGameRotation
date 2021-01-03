package com.gamecodeschool.simplegameloop;

import android.content.Context;

public class GameState {

    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;
    private long mStartTimeMilis;
    private EngineController mEngineController;

    GameState(EngineController gs, Context context){

        mEngineController = gs;
    }

    void stopThread(){ mThreadRunning = false; }
    boolean getThreadRunning(){return mThreadRunning; }
    void startThread(){ mThreadRunning = true; }


    boolean getPaused(){ return mPaused; }


    void startNewGame(){
        stopEverything();
        mEngineController.startNewLevel();
        startEverything();
        mStartTimeMilis = System.currentTimeMillis();
    }

    void endGame(){
        stopThread();
        stopEverything();
    }

    private void startEverything() {
        mPaused = false;
        mGameOver = false;
        mDrawing = true;
    }

    void stopEverything() {
        mPaused = true;
        mGameOver = true;
        mDrawing = false;
    }

}
