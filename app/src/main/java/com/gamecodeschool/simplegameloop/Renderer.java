package com.gamecodeschool.simplegameloop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Renderer {

    private Canvas mCanvas;
    private ArrowSpec mArrowSpec;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Context mContext;
    private Point mScreenSize;
    Matrix mMatrix;



    Renderer(SurfaceView sh, Point screenSize, Context context, ArrowSpec arrowSpec){

        mSurfaceHolder = sh.getHolder();
        mPaint = new Paint();
        mContext = context;
        mScreenSize = screenSize;
        mArrowSpec = arrowSpec;

    }


    void draw( GameState gs){

        if( mSurfaceHolder.getSurface().isValid()){
            mCanvas = mSurfaceHolder.lockCanvas();

            mArrowSpec.draw(mCanvas,mPaint);

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

}