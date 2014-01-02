package com.polysfactory.cv.template;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraViewEx;
import org.opencv.core.Mat;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.polysfactory.cv.util.GlassUtil;

public class MainActivity extends Activity implements CvCameraViewListener2 {

    private JavaCameraViewEx mCameraView;
    Mat mCaptured;
    float mScale;
    int mCameraViewOffsetX = 0;
    int mCameraViewOffsetY = 0;
    int mScreenWidth = 0;
    int mScreenHeight = 0;
    long mDisplayedPower = 0;
    int mPreviewWidth;
    int mPreviewHeight;
    Mat mCurrentFrame;
    GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mCameraView = (JavaCameraViewEx) findViewById(R.id.camera_view);
        mCameraView.setCvCameraViewListener(this);
        mCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_ANY);
        mCameraView.enableFpsMeter();
        mCameraView.setMaxFrameSize(640, 360);

        if (GlassUtil.isGlass()) {
            mGestureDetector = createGestureDetector(this);
        }
    }

    @Override
    protected void onResume() {
        super.onStart();
        mCameraView.enableView();
    }

    @Override
    protected void onPause() {
        super.onStop();
        mCameraView.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame frame) {
        mCurrentFrame = frame.rgba().clone();
        return mCurrentFrame;
    }

    @Override
    public void onCameraViewStarted(int w, int h) {
        mScale = mCameraView.getScale();
        mPreviewWidth = w;
        mPreviewHeight = h;
        mScreenWidth = mCameraView.getWidth();
        mScreenHeight = mCameraView.getHeight();
        mCameraViewOffsetX = (mScreenWidth - (int) (w * mScale)) / 2;
        mCameraViewOffsetY = (mScreenHeight - (int) (h * mScale)) / 2;
        Log.d(C.TAG, String.format("screen_size=(%d,%d)", mScreenWidth, mScreenHeight));
        Log.d(C.TAG, String.format("view_size=(%d,%d) frame_size=(%d,%d) offset=(%d,%d)", mCameraView.getWidth(),
                mCameraView.getHeight(), w, h, mCameraViewOffsetX, mCameraViewOffsetY));
    }

    @Override
    public void onCameraViewStopped() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_CAMERA) {
            // Stop the preview and release the camera.
            // Execute your logic as quickly as possible
            // so the capture happens quickly.
            mCameraView.disableView();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return super.onGenericMotionEvent(event);
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    Log.d(C.TAG, "Tap");
                } else if (gesture == Gesture.TWO_TAP) {
                    Log.d(C.TAG, "Two Tap");
                }
                return false;
            }
        });
        return gestureDetector;
    }
}
