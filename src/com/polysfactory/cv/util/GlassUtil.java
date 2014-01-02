package com.polysfactory.cv.util;

import android.os.Build;

public class GlassUtil {
    public static boolean isGlass() {
        return Build.MODEL.toUpperCase().contains("GLASS");
    }
}
