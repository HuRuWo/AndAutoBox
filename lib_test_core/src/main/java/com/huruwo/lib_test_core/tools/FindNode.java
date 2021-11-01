package com.huruwo.lib_test_core.tools;

import android.view.accessibility.AccessibilityNodeInfo;

import com.huruwo.lib_test_core.TestAccessibilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FindNode {

    TestAccessibilityService accService;

    public FindNode() {
        accService = TestAccessibilityService.getmAccessibilityService();
        if(accService==null){
            throw  new RuntimeException("AccessibilityService Exception");
        }
    }

    public AccessibilityNodeInfo findRootNode(){
        if(accService!=null) {
            return accService.getRootInActiveWindow();
        }else {
            return null;
        }
    }

    public List<AccessibilityNodeInfo> findNodeListByText(String text){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
              return root.findAccessibilityNodeInfosByText(text);
        }else {
            return new ArrayList<>();
        }
    }

    public List<AccessibilityNodeInfo> findNodeListByViewId(String viewId){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
            return root.findAccessibilityNodeInfosByViewId(viewId);
        }else {
            return new ArrayList<>();
        }
    }

    public List<AccessibilityNodeInfo> findNodeListByViewIdText(String viewId,String text){
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


    public AccessibilityNodeInfo findNodeByText(String text,int index){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
            List<AccessibilityNodeInfo> nodeInfos =  findNodeListByText(text);
            if(nodeInfos.size()>0&&index<nodeInfos.size()){
                return nodeInfos.get(index);
            }
        }
        return null;
    }

    public AccessibilityNodeInfo findNodeByViewId(String viewId,int index){
        AccessibilityNodeInfo root = findRootNode();
        if(root!=null){
            List<AccessibilityNodeInfo> nodeInfos =  findNodeListByViewId(viewId);
            if(nodeInfos.size()>0&&index<nodeInfos.size()){
                return nodeInfos.get(index);
            }
        }
        return null;
    }

    public AccessibilityNodeInfo findNodeByViewIdText(String viewId,String text,int index){
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
