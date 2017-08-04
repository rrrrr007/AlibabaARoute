package com.example.ch.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * Created by ch on 2017/8/4.
 */
@Interceptor(priority = 5, name = "测试拦截")
public class TestInterceptor implements IInterceptor {
    private Context context;
    private Handler handler;
    @Override
    public void process(final Postcard postcard, final InterceptorCallback callback) {
        if (!"/test/activity".equals(postcard.getPath())){
            callback.onContinue(postcard);
            return;
        }
        final AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.instance);
        ab.setCancelable(false);
        ab.setTitle("温馨提醒");
        ab.setMessage("想要跳转到TestActivity么？(触发了\"/inter/test1\"拦截器，拦截了本次跳转)");
        ab.setNegativeButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onContinue(postcard);
            }
        });
        ab.setNeutralButton("算了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onInterrupt(null);
            }
        });
        ab.setPositiveButton("加点料", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                postcard.withString("name", "我是在拦截器中附加的参数");
                callback.onContinue(postcard);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ab.create().show();
            }
        });




        //callback.onContinue(postcard);  // 处理完成，交还控制权
        // callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程

        // 以上两种至少需要调用其中一种，否则不会继续路由
    }

    @Override
    public void init(Context context) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        this.context=context;
        Log.e("ch","我在初始化拦截器");
    }

    public void runOnUiThread(Runnable runnable) {
        if (handler==null){
            handler = new Handler(Looper.getMainLooper());
        }
        if(Looper.getMainLooper().equals(Looper.myLooper())) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }
}
