package com.polysfactory.cv.template;

import org.opencv.android.OpenCVLoader;

import android.app.Application;

public class CvApplication extends Application {
    static {
        if (OpenCVLoader.initDebug()) {
            System.loadLibrary("template");
        } else {
            // Handle initialization error
        }
    }
}
