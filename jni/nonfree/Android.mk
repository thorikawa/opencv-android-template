LOCAL_PATH_ORIG := $(LOCAL_PATH)
LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

OPENCV_INSTALL_MODULES:=on
OPENCV_CAMERA_MODULES:=on
OPENCV_LIB_TYPE:=SHARED

include /Users/poly/workspace/OpenCV-2.4.6-android-sdk/sdk/native/jni/OpenCV.mk

LOCAL_C_INCLUDES:= $(LOCAL_PATH) /Users/poly/workspace/OpenCV-2.4.6-android-sdk/sdk/native/jni/include
LOCAL_MODULE    := nonfree
LOCAL_CFLAGS    := -Werror -O3 -ffast-math
LOCAL_LDLIBS    += -llog -ldl
LOCAL_ARM_NEON := true
LOCAL_SRC_FILES := nonfree_init.cpp precomp.cpp sift.cpp surf.cpp

include $(BUILD_SHARED_LIBRARY)

LOCAL_PATH := $(LOCAL_PATH_ORIG)