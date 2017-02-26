package com.example.enzo.autohidehelper;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class CustomBehaviorActivity extends AppCompatActivity {
    private RecyclerView list;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_behavior);

        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new ListAdapter());

        fab = (FloatingActionButton) findViewById(R.id.fab);
        AutoHideHelper hideHelper = new AutoHideHelper(this, fab, list, new CustomBehavior());
    }

    private class CustomBehavior extends AutoHideHelper.BehaviorRules {
        @Override
        public void onDown() {
            hide();
        }

        @Override
        public void onUp() {
            show();
        }
    }
}
