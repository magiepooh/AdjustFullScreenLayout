package com.github.magiepooh.adjustfullscreenlayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by magiepooh on 2016/07/15.
 */
public class AdjustFullScreenLayout extends FrameLayout {

  private ScreenState screenState = ScreenState.NORMAL;
  private DisplayCalculator calculator;

  public AdjustFullScreenLayout(Context context) {
    this(context, null);
  }

  public AdjustFullScreenLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AdjustFullScreenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    calculator = DisplayCalculator.with(context);
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();
    updatePadding();
  }

  @Override protected void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    updatePadding();
  }

  public void toFullScreen() {
    ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
    final int left = getPaddingLeft();
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float fraction = (Float) animation.getAnimatedValue();
        int top = Math.round(calculator.getStatusBarHeight() * fraction);
        int right = Math.round(calculator.getNavigationBarWidth() * fraction);
        int bottom = Math.round(calculator.getNavigationBarHeight() * fraction);
        setPadding(left, top, right, bottom);
      }
    });
    animator.start();
    screenState = ScreenState.FULL;
  }

  public void toNormalScreen() {
    ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
    final int left = getPaddingLeft();
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float fraction = (Float) animation.getAnimatedValue();
        int top = Math.round(calculator.getStatusBarHeight() * fraction);
        int right = Math.round(calculator.getNavigationBarWidth() * fraction);
        int bottom = Math.round(calculator.getNavigationBarHeight() * fraction);
        setPadding(left, top, right, bottom);
      }
    });
    animator.start();
    screenState = ScreenState.NORMAL;
  }

  public boolean isFullScreen() {
    return screenState == ScreenState.FULL;
  }

  private void updatePadding() {
    if (screenState == ScreenState.NORMAL) {
      setPadding(getPaddingLeft(), calculator.getStatusBarHeight(),
          calculator.getNavigationBarWidth(), calculator.getNavigationBarHeight());
    } else {
      setPadding(getPaddingLeft(), 0, 0, 0);
    }
  }

  public enum ScreenState {
    NORMAL,
    FULL
  }
}
