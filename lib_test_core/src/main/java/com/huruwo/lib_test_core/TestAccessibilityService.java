package com.huruwo.lib_test_core;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class TestAccessibilityService extends AccessibilityService {


    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        com.huruwo.lib_test_core.tools.ActionNode.mAS = this;
    }


}
