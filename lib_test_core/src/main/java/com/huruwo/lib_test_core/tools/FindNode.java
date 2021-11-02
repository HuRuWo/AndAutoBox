package com.huruwo.lib_test_core.tools;

import static com.huruwo.lib_test_core.tools.ActionNode.mAS;

import android.view.accessibility.AccessibilityNodeInfo;

import com.huruwo.lib_test_core.TestAccessibilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FindNode {


    public static AccessibilityNodeInfo findRootNode(){
        if(mAS!=null) {
            return mAS.getRootInActiveWindow();
        }else {
            return null;
        }
    }

    public static List<AccessibilityNodeInfo> findNodeListByText(String text){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
              return root.findAccessibilityNodeInfosByText(text);
        }else {
            return new ArrayList<>();
        }
    }

    public static List<AccessibilityNodeInfo> findNodeListByViewId(String viewId){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
            return root.findAccessibilityNodeInfosByViewId(viewId);
        }else {
            return new ArrayList<>();
        }
    }

    public static List<AccessibilityNodeInfo> findNodeListByViewIdText(String viewId,String text){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
            List<AccessibilityNodeInfo> nodeInfos =  findNodeListByViewId(viewId);
            List<AccessibilityNodeInfo> nodeInfos2 =  new ArrayList<>();
            for(AccessibilityNodeInfo info:nodeInfos){
                if(info.getText()!=null&&info.getText().toString().equals(text)){
                    nodeInfos2.add(info);
                }
            }
            return nodeInfos;
        }else {
            return new ArrayList<>();
        }
    }


    public static AccessibilityNodeInfo findNodeByText(String text,int index){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
            List<AccessibilityNodeInfo> nodeInfos =  findNodeListByText(text);
            if(nodeInfos.size()>0&&index<nodeInfos.size()){
                return nodeInfos.get(index);
            }
        }
        return null;
    }

    public static AccessibilityNodeInfo findNodeByViewId(String viewId,int index){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
            List<AccessibilityNodeInfo> nodeInfos =  findNodeListByViewId(viewId);
            if(nodeInfos.size()>0&&index<nodeInfos.size()){
                return nodeInfos.get(index);
            }
        }
        return null;
    }

    public static AccessibilityNodeInfo findNodeByViewIdText(String viewId,String text,int index){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
            List<AccessibilityNodeInfo> nodeInfos =  findNodeListByViewIdText(viewId,text);
            if(nodeInfos.size()>0&&index<nodeInfos.size()){
                return nodeInfos.get(index);
            }
        }
        return null;
    }

}
