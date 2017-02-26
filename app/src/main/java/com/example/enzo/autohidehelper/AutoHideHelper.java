package com.example.enzo.autohidehelper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * <p>
 * Created by ZENG Yuhao(Enzo)<br>
 * Contact: enzo.zyh@gmail.com
 * </p>
 */

public class AutoHideHelper {

    public static final int STATE_SHOWING = 0;
    public static final int STATE_HIDING = 2;

    private int mVisibilityState = STATE_SHOWING;

    public static final int DEFAULT_SENSIBILITY = 4;
    private int mSensibility = DEFAULT_SENSIBILITY;

    private Context mContext;
    private View mTargetView, mTriggerView;
    private BehaviorRules mBehaviorRules;

    public static final int DEFAULT_ANIMATION_DURATION = 200;
    private Animator mAnimShow, mAnimHide;


    public AutoHideHelper(Context context, View target, View trigger) {
        this(context, target, trigger, new DefaultBehaviorRules());
    }

    public AutoHideHelper(Context context, View target, View trigger, BehaviorRules rules) {
        if (context == null)
            throw new IllegalArgumentException("You have passed in a null context");

        if (target == null)
            throw new IllegalArgumentException("You have passed in a null target view.");

        if (trigger == null)
            throw new IllegalArgumentException("You have passed in a null trigger view.");

        if (rules == null)
            throw new IllegalArgumentException("You have passed in a null BehaviorRules.");

        mContext = context;
        mTargetView = target;
        mTriggerView = trigger;

        mBehaviorRules = rules;
        mBehaviorRules.bind(this);

        setShowAnimation(ObjectAnimator.ofFloat(mTargetView, "alpha", 1).setDuration(DEFAULT_ANIMATION_DURATION));
        setHideAnimation(ObjectAnimator.ofFloat(mTargetView, "alpha", 0).setDuration(DEFAULT_ANIMATION_DURATION));
    }

    public void setVisibilityState(int state) {
        switch (state) {
            case STATE_SHOWING:
            case STATE_HIDING:
                mVisibilityState = state;
                break;
        }
    }

    public int getVisibilityState() {
        return mVisibilityState;
    }

    public void setSensibility(int sensibility) {
        mSensibility = sensibility;
    }

    public int getSensibility() {
        return mSensibility;
    }

    public void setShowAnimation(Animator animator) {
        if (animator == null) return;

        if (mAnimShow != null && mAnimShow.isRunning()) return;

        mAnimShow = animator;
        mAnimShow.setTarget(mTargetView);
    }

    public void setHideAnimation(Animator animator) {
        if (animator == null) return;

        if (mAnimHide != null && mAnimHide.isRunning()) return;

        mAnimHide = animator;
        mAnimHide.setTarget(mTargetView);
    }

    public void setRules(BehaviorRules rules) {
        if (rules != null) {
            mBehaviorRules.unbind();
            mBehaviorRules = rules;
            mBehaviorRules.bind(this);
        }
    }

    private void requestShowing() {
        if (mVisibilityState == STATE_HIDING) {
            mVisibilityState = STATE_SHOWING;
            // cancel current animation
            if (mTargetView.getAnimation() != null) {
                mTargetView.getAnimation().cancel();
            }
            mAnimShow.start();
        }
    }

    private void requestHiding() {
        if (mVisibilityState == STATE_SHOWING) {
            mVisibilityState = STATE_HIDING;
            // cancel current animation
            if (mTargetView.getAnimation() != null) mTargetView.getAnimation().cancel();
            mAnimHide.start();
        }
    }

    public static class BehaviorRules {
        private AutoHideHelper mHelperBound;
        private GestureDetector mGestureDetector;

        protected void bind(AutoHideHelper helper) {
            if (helper != null) {
                mHelperBound = helper;
                // bind GestureDetector to AutoHideHelper's trigger view.
                mGestureDetector = new GestureDetector(mHelperBound.mContext, new GestureListener());
                mHelperBound.mTriggerView.setOnTouchListener(new TouchListener());
            } else
                throw new IllegalArgumentException("You have bound a BehaviourRules to a null AutoHideHelper.");
        }

        protected void unbind() {
            mHelperBound.mTriggerView.setOnTouchListener(null);
            mHelperBound = null;
        }

        protected void show() {
            if (mHelperBound != null)
                mHelperBound.requestShowing();
        }

        protected void hide() {
            if (mHelperBound != null)
                mHelperBound.requestHiding();
        }

        public void onDown() {
            // further implementation and personalisation required.
        }

        public void onUp() {
            // further implementation and personalisation required.
        }

        public void onScrollUp() {
            // further implementation and personalisation required.
        }

        public void onScrollDown() {
            // further implementation and personalisation required.
        }

        public void onScrollLeft() {
            // further implementation and personalisation required.
        }

        public void onScrollRight() {
            // further implementation and personalisation required.
        }

        private class GestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                if (v1 > mHelperBound.mSensibility)
                    onScrollUp();
                else if (v1 < -mHelperBound.mSensibility)
                    onScrollDown();

                if (v > mHelperBound.mSensibility)
                    onScrollLeft();
                else if (v < -mHelperBound.mSensibility)
                    onScrollRight();
                return false;
            }
        }

        private class TouchListener implements View.OnTouchListener {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mGestureDetector != null)
                    mGestureDetector.onTouchEvent(motionEvent);

                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    onUp();
                else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    onDown();
                return false;
            }
        }
    }

    private static class DefaultBehaviorRules extends BehaviorRules {
        @Override
        public void onScrollUp() {
            hide();
        }

        @Override
        public void onScrollDown() {
            show();
        }
    }
}
