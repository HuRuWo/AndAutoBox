package com.huruwo.lib_test_core.tools;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.huruwo.lib_test_core.TestAccessibilityService;

public class ActionNode {

    public enum ClickAction{
        AutoClick,AutoDoubleClick,AutoLongClick
    }

    public static boolean actionClickNode(String viewId,String text,int index,ClickAction clickAction,long sleep){
        AccessibilityNodeInfo info = null;

        if(TextUtils.isEmpty(viewId)&&TextUtils.isEmpty(text)){
            return false;
        }else


        if(!TextUtils.isEmpty(viewId)&&TextUtils.isEmpty(text)){
           info =new FindNode().findNodeByViewId(viewId,index);
        }else

        if(TextUtils.isEmpty(viewId)&&!TextUtils.isEmpty(text)){
            info =new FindNode().findNodeByText(text,index);
        }else


        if(!TextUtils.isEmpty(viewId)&&!TextUtils.isEmpty(text)){
            info =new FindNode().findNodeByViewIdText(viewId,text,index);
        }




        boolean is  = false;
        if(info!=null){
            switch (clickAction){
                case AutoClick:
                     is =  performAutoClick(info);
                     break;
                case AutoLongClick:
                    is = performAutoLongClick(info);
                    break;
                case AutoDoubleClick:
                    is =  performAutoDoubleClick(info);
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

    public static void goAutoHome(long sleep) {
        TestAccessibilityService service = TestAccessibilityService.getmAccessibilityService();
        if (service != null) {
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
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
