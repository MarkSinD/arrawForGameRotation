package com.gamecodeschool.simplegameloop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.Log;

public class ArrowSpec {

    private boolean misActive = false;
    private static final String bitmapName = "arrow";
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private PointF mLocation;
    private PointF mPointCenter;
    private int mObjectHeight;
    private int mObjectWidth;
    private double mSpeed = 4;
    private double mSpeedX;
    private double mSpeedY;
    private static Point mScreenSize;
    double mAngle;
    double mAngleForFunc;



    ArrowSpec(Context c, Point screenSize){
        mScreenSize = screenSize;
        mObjectHeight = mScreenSize.y / 2;
        mObjectWidth = mScreenSize.x / 50;
        mLocation = new PointF();
        mPointCenter = new PointF();

        int resID = c.getResources().getIdentifier(bitmapName, "drawable", c.getPackageName());
        mBitmap = BitmapFactory.decodeResource(c.getResources(), resID);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mObjectWidth, mObjectHeight, false);
        mMatrix = new Matrix();
    }
    public void setAngle(double angle){
        mAngle = angle;
        if(mAngle < 90){
            mAngleForFunc = 90 - mAngle;
            mSpeedX = mSpeed * Math.cos(Math.toRadians(mAngleForFunc));
            mSpeedY = -mSpeed * Math.sin(Math.toRadians(mAngleForFunc));
        }
        else if(mAngle >= 90 & mAngle < 180){
            mAngleForFunc = mAngle - 90;
            mSpeedX = mSpeed * Math.cos(Math.toRadians(mAngleForFunc));
            mSpeedY = mSpeed * Math.sin(Math.toRadians(mAngleForFunc));
        }
        else if(mAngle >= 180 && mAngle < 270) {
            mAngleForFunc = 270 - mAngle;
            mSpeedX = -mSpeed * Math.cos(Math.toRadians(mAngleForFunc));
            mSpeedY = mSpeed * Math.sin(Math.toRadians(mAngleForFunc));
        }
        else if(mAngle >= 270 & mAngle <= 360){
            mAngleForFunc = mAngle - 270;
            mSpeedX = -mSpeed * Math.cos(Math.toRadians(mAngleForFunc));
            mSpeedY = -mSpeed * Math.sin(Math.toRadians(mAngleForFunc));
        }
        Log.e("SpeedX = ", " " + mSpeedX);
        Log.e("SpeedY = ", " " + mSpeedY);
    }

    public void spawn(Point startPoint, double angle){
        mLocation.x = startPoint.x - mObjectWidth/2;
        mLocation.y = startPoint.y - mObjectHeight/2;
        mAngle = angle;
        mPointCenter.x = (mLocation.x + (mLocation.x + mObjectWidth)) / 2;
        mPointCenter.y = (mLocation.y + (mLocation.y+mObjectHeight)) / 2;
    }

    public void setIsActive(boolean b){
        misActive = b;
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawColor(Color.argb(255, 222,16,16));
        paint.setColor(Color.WHITE);
        canvas.drawLine(0,mScreenSize.y/2, mScreenSize.x, mScreenSize.y/2,paint);
        canvas.drawLine(mScreenSize.x/2,0, mScreenSize.x/2,mScreenSize.y, paint);
        mMatrix.setTranslate(mLocation.x, mLocation.y);
        mMatrix.postRotate((int)mAngle,mPointCenter.x, mPointCenter.y);
        canvas.drawBitmap(mBitmap,mMatrix,paint);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        canvas.drawPoint(mLocation.x, mLocation.y, paint);
        canvas.drawPoint(mLocation.x + mObjectWidth, mLocation.y + mObjectHeight, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawPoint(mPointCenter.x, mPointCenter.y, paint);
    }


    public boolean move(){
        if(misActive) {
            mLocation.x = (float) (mLocation.x + mSpeedX);
            mLocation.y = (float) (mLocation.y + mSpeedY);
            mPointCenter.x = (mLocation.x + (mLocation.x + mObjectWidth)) / 2;
            mPointCenter.y = (mLocation.y + (mLocation.y + mObjectHeight)) / 2;
        }
        return true;
    }

}
