package com.example.ch.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/test/activity")
public class Main2Activity extends AppCompatActivity {
    @Autowired
    public String name;
    @Autowired
    int age;
    @Autowired(name = "girl") // 通过name来映射URL中的不同参数
            boolean boy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ARouter.getInstance().inject(this);//不用通过getIntent().getExtra等初始化；
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Main2Activity.this, "name"+getIntent().getStringExtra("name"), Toast.LENGTH_SHORT).show();
                Toast.makeText(Main2Activity.this, "name"+name+"age"+age+"boy"+boy, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
