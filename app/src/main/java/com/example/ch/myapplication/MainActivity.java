package com.example.ch.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //简单跳转：
                //ARouter.getInstance().build("test/activity").navigation();
                // 页面主动指定了Group名
                //ARouter.getInstance().build("/module/2", "m2").navigation();
                //带参数跳转：with = putExtra;
                ARouter.getInstance()
                        .build("/test/activity")
                        .withString("name", "老王")
                        .withInt("age", 23)
                        //.with(params)直接传 Bundle params = new Bundle();
                        //.withFlags();知道flag;
                        //.withObject("key", new TestObj("Jack", "Rose"))对象传递
                        .greenChannel()//跳过所有的拦截器
                        .navigation();
                break;
            case R.id.btn2:
                //老版动画
                ARouter.getInstance()
                        .build("/test/activity")
                        .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                        .navigation(this);
                break;
            case R.id.btn3:
                //新版动画
                if (Build.VERSION.SDK_INT >= 16) {
                    ActivityOptionsCompat compat = ActivityOptionsCompat.
                            makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);

                    ARouter.getInstance()
                            .build("/test/activity")
                            .withOptionsCompat(compat)
                            .navigation();
                } else {
                    Toast.makeText(this, "API < 16,不支持新版本动画", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn4:
                //添加拦截处理；
                ARouter.getInstance()
                        .build("/test/activity")
                        .navigation(this, new NavCallback() {
                            @Override
                            public void onArrival(Postcard postcard) {
                                Log.e("ch", "未拦截");
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                Log.e("ch", "拦截");
                            }
                        });
                break;
            case R.id.btn5:
                //通过URL 跳转
                    Uri testUri = Uri.parse("arouter://m.aliyun.com/some/kk");
                    ARouter.getInstance().build(testUri)
                            .withString("key1", "value1")
                            .greenChannel()
                            .navigation();
                break;
            case R.id.btn6:
                break;
            case R.id.btn7:
                break;
            case R.id.btn8:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }


}
