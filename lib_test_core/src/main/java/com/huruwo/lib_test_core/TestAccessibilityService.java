package com.huruwo.lib_test_core;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class TestAccessibilityService extends AccessibilityService {

    public static TestAccessibilityService mAccessibilityService;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAccessibilityService = this;
    }

    public static TestAccessibilityService getmAccessibilityService() {
        return mAccessibilityService;
    }

}
