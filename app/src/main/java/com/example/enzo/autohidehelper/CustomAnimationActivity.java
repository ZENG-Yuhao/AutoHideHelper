package com.example.enzo.autohidehelper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class CustomAnimationActivity extends AppCompatActivity {
    private RecyclerView list;
    private FloatingActionButton fab;
    private float fab_y;
    private int list_h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_animation);

        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new ListAdapter());


        fab = (FloatingActionButton) findViewById(R.id.fab);
        final AutoHideHelper hideHelper = new AutoHideHelper(this, fab, list);


        fab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // get size params to calculate animations
                list_h = list.getHeight();
                fab_y = fab.getY();

                // define new animations and pass them into the AutoHideHelper
                Animator animShow = ObjectAnimator.ofFloat(null, "y", fab_y);
                animShow.setDuration(200);
                animShow.setInterpolator(new AccelerateInterpolator());

                Animator animHide = ObjectAnimator.ofFloat(null, "y", list_h);
                animHide.setDuration(200);
                animHide.setInterpolator(new DecelerateInterpolator());

                hideHelper.setShowAnimation(animShow);
                hideHelper.setHideAnimation(animHide);

                // remove listener
                fab.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }
}
