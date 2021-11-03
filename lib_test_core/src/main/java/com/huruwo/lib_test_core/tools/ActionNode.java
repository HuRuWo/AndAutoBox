package com.huruwo.lib_test_core.tools;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.huruwo.lib_test_core.TestAccessibilityService;

import java.util.List;

public class ActionNode {

    public static TestAccessibilityService mAS;


    public enum ClickAction {
        AutoClick, AutoDoubleClick, AutoLongClick,
        GestureClick,GestureDoubleClick,GestureLongClick
    }

    public static boolean actionClickNode(String viewId, String text, int index, ClickAction clickAction, long sleep) {
        AccessibilityNodeInfo info = null;

        if (TextUtils.isEmpty(viewId) && TextUtils.isEmpty(text)) {
            return false;
        } else if (!TextUtils.isEmpty(viewId) && TextUtils.isEmpty(text)) {
            info = FindNode.findNodeByViewId(viewId, index);
        } else if (TextUtils.isEmpty(viewId) && !TextUtils.isEmpty(text)) {
            info = FindNode.findNodeByText(text, index);
        } else if (!TextUtils.isEmpty(viewId) && !TextUtils.isEmpty(text)) {
            info = FindNode.findNodeByViewIdText(viewId, text, index);
        }


        boolean is = false;
        if (info != null) {
            switch (clickAction) {
                case AutoClick:
                    is = performAutoClick(info);
                    break;
                case AutoLongClick:
                    is = performAutoLongClick(info);
                    break;
                case AutoDoubleClick:
                    is = performAutoDoubleClick(info);
                    break;
                case GestureClick:
                    is = useGestureClickNode(info);
                    break;
                case GestureDoubleClick:
                    is = useGestureDoubleClickNode(info);
                    break;
                case GestureLongClick:
                    is = useGestureLongClickNode(info,2000,null);
                    break;
                default:
                    break;
            }
            actionPause(sleep);
        }
        return is;
    }



    public static boolean performAutoClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        if (nodeInfo.isClickable()) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            return true;
        } else {
            performAutoClick(nodeInfo.getParent());
        }
        return false;
    }

    public static boolean performAutoDoubleClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        performAutoClick(nodeInfo);
        actionPause(100);
        performAutoClick(nodeInfo);
        return false;
    }

    public static boolean performAutoLongClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        if (nodeInfo.isClickable()) {
            //nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
        } else {
            return performAutoLongClick(nodeInfo.getParent());
        }
    }

    public static boolean performAutoSlide(AccessibilityNodeInfo nodeInfo, boolean direction) {

        if (nodeInfo == null) {
            return false;
        }
        if (nodeInfo.isScrollable()) {
            //向前滑
            if (direction) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            } else {
                //向后滑
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
            }
            return true;
        }

        return false;
    }

    public static boolean idAutoInput(String viewId, int index, String content) {

        AccessibilityNodeInfo info = FindNode.findNodeByViewId(viewId, index);
        if(info!=null){
           return inputAutoText(info,content);
        }
        return false;
    }

    public static boolean inputAutoText(AccessibilityNodeInfo nodeInfo, String text) {

        if (nodeInfo == null) {
            return false;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        }

        return false;
    }


    public static boolean useGestureClickPoint(Point point) {

        if (mAS == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            GestureDescription.Builder builder = new GestureDescription.Builder();
            Path path = new Path();
            if (point.x >= 0 && point.y >= 0) {
                path.moveTo(point.x, point.y);
                GestureDescription gestureDescription = builder
                        .addStroke(new GestureDescription.StrokeDescription(path, 0, 50))
                        .build();
                mAS.dispatchGesture(gestureDescription, null, null);
                return true;
            }
        }
        return false;
    }

    public static boolean useGestureClickNode(AccessibilityNodeInfo info) {
        if (mAS == null||info == null) {
            return false;
        }
        Rect rect = new Rect();
        info.getBoundsInScreen(rect);
        return useGestureClickPoint(new Point(rect.centerX(), rect.centerY()));
    }

    public static boolean useGestureDoubleClickPoint(Point point) {

        if (mAS == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            useGestureClickPoint(point);
            actionPause(100);
            useGestureClickPoint(point);
        }
        return false;
    }

    public static boolean useGestureDoubleClickNode(AccessibilityNodeInfo info) {
        if (mAS == null||info == null) {
            return false;
        }
        Rect rect = new Rect();
        info.getBoundsInScreen(rect);
        return useGestureDoubleClickPoint(new Point(rect.centerX(), rect.centerY()));
    }

    public static boolean useGestureLongClickPoint(Point point,int time ,AccessibilityService.GestureResultCallback gestureResultCallback) {

        if (mAS == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            GestureDescription.Builder builder = new GestureDescription.Builder();
            if (point.x >= 0 && point.y >= 0) {
                Path path = new Path();
                path.moveTo(point.x, point.y);
                GestureDescription gestureDescription = builder
                        .addStroke(new GestureDescription.StrokeDescription(path, 0, time))
                        .build();
                boolean is = mAS.dispatchGesture(gestureDescription, gestureResultCallback, null);
                actionPause(time); //在线程里等待 直到点击完毕
                return is;
            }
        }
        return false;
    }

    public static boolean useGestureLongClickNode(AccessibilityNodeInfo info, int time, AccessibilityService.GestureResultCallback gestureResultCallback) {

        if (mAS == null||info == null) {
            return false;
        }


        Rect rect = new Rect();
        info.getBoundsInScreen(rect);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return useGestureLongClickPoint(new Point(rect.centerX(),rect.centerY()),time,gestureResultCallback);
        }

        return false;
    }





    public static void performAutoBack(long sleep) {
        if (mAS == null) {
            return;
        } else {
            mAS.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            actionPause(sleep);
        }
    }

    public static void goAutoHome(long sleep) {
        if (mAS != null) {
            mAS.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
            actionPause(sleep);
        }
    }



    public static void actionPause(long millis) {
        if (millis > 0) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
