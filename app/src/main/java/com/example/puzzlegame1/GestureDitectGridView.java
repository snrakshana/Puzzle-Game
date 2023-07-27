package com.example.puzzlegame1;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

public class GestureDitectGridView extends GridView {
    private GestureDetector gDetector;
    private boolean mFlingConfirmed = false;
    private float mTouchX;
    private float mTouchY;

    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    public GestureDitectGridView(Context context){
        super(context);
        init(context);
    }

    public GestureDitectGridView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public GestureDitectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @TargetApi(android.os.Build.VERSION_CODES.LOLLIPOP)
    public GestureDitectGridView(Context context, AttributeSet attrs , int defStyleAttr,int defSyleRes){
        super(context,attrs,defStyleAttr,defSyleRes);
        init(context);
    }

    private void init(final Context context){
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent event){
                return true;
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityx, float velocityY){
                final int position = GestureDitectGridView.this.pointToPosition
                        (Math.round(e1.getX()),Math.round(e1.getY()));

                if (Math.abs(e1.getY()-e2.getY())> SWIPE_MAX_OFF_PATH){
                    if (Math.abs(e1.getX()-e2.getX())> SWIPE_MAX_OFF_PATH || Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
                        return false;
                    }
                    if (e1.getY()-e2.getY() > SWIPE_MIN_DISTANCE){
                        MainActivity.moveTiles(context, MainActivity.UP,position);
                    }
                    else if (e2.getY()-e1.getY()>SWIPE_MIN_DISTANCE){
                        MainActivity.moveTiles(context, MainActivity.DOWN,position);
                    }
                }else {
                    if(Math.abs(velocityx) <SWIPE_THRESHOLD_VELOCITY){
                        return false;
                    }
                    if (e1.getX()-e2.getX()> SWIPE_MIN_DISTANCE){
                        MainActivity.moveTiles(context, MainActivity.LEFT,position);
                    }
                    else if (e2.getX()-e1.getX()>SWIPE_MIN_DISTANCE){
                        MainActivity.moveTiles(context, MainActivity.RIGHT,position);
                    }
                }
                return super.onFling(e1,e2,velocityx,velocityY);
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        int action=ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if(action==MotionEvent.ACTION_CANCEL || action==MotionEvent.ACTION_UP){
            mFlingConfirmed=false;
        } else if (action==MotionEvent.ACTION_DOWN){
            mTouchX=ev.getX();
            mTouchY=ev.getY();
        } else {
            if (mFlingConfirmed) {
                return true;
            }
            float dX=(Math.abs(ev.getX()-mTouchX));
            float dY=(Math.abs(ev.getY()-mTouchY));
            if((dX>SWIPE_MIN_DISTANCE)||(dY>SWIPE_MIN_DISTANCE)){
                mFlingConfirmed=true;
                return true;
            }
        }
        return  super.onInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev){
        return gDetector.onTouchEvent(ev);
    }
}



















