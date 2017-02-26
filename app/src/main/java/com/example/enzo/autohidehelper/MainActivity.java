package com.example.enzo.autohidehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_default, btn_custom_anim, btn_custom_behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_default = (Button) findViewById(R.id.btn_default);
        btn_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DefaultActivity.class);
                startActivity(intent);
            }
        });

        btn_custom_anim = (Button) findViewById(R.id.btn_custom_anim);
        btn_custom_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomAnimationActivity.class);
                startActivity(intent);
            }
        });

        btn_custom_behavior = (Button) findViewById(R.id.btn_custom_behavior);
        btn_custom_behavior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomBehaviorActivity.class);
                startActivity(intent);
            }
        });
    }
}
