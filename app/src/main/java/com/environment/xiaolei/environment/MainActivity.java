package com.environment.xiaolei.environment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button  btn_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_test=findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerConfig.init(MainActivity.this);
                Toast.makeText(MainActivity.this,
                     "this is   "+ServerConfig.getServerMode(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
