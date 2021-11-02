package com.huruwo.fasttest;

import static com.huruwo.lib_test_core.tools.ActionNode.actionPause;
import static com.huruwo.lib_test_core.tools.ActionNode.mAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huruwo.lib_test_core.TestAccessibilityService;
import com.huruwo.lib_test_core.tools.ActionNode;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.textView);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });



        button1.setOnClickListener(view -> {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ActionNode.goAutoHome(2000);
                    ActionNode.actionClickNode("","相机",0,ActionNode.ClickAction.AutoClick,5000);
                    ActionNode.actionClickNode("com.meizu.media.camera:id/shutter_button","",0,ActionNode.ClickAction.AutoClick,2000);
                }
            }).start();

        });


        button2.setOnClickListener(view -> {

            ActionNode.goAutoHome(2000);
            ActionNode.actionClickNode("","信息",0,ActionNode.ClickAction.AutoClick,2000);


        });

        button3.setOnClickListener(view -> {

        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(isAccessibilitySettingsOn()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("已正常授予应用无障碍权限");
                                textView.setTextColor(Color.GREEN);
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("要正常运行请先授予应用无障碍权限(点击前往)");
                                textView.setTextColor(Color.RED);
                            }
                        });
                    }
                    actionPause(3000);
                }
            }
        }).start();

    }

    public static boolean isAccessibilitySettingsOn() {
        if (mAS!= null) {
            return true;
        }
        return false;
    }
}