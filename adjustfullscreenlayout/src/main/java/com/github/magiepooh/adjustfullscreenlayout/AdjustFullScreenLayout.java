package com.github.magiepooh.adjustfullscreenlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by magiepooh on 2016/07/15.
 */
public class AdjustFullScreenLayout extends FrameLayout
    implements DisplayCalculator.WindowInsetsChangeListener {

  private ScreenState currentScreenState = ScreenState.NORMAL;
  private ScreenState lastScreenState = ScreenState.NORMAL;
  private DisplayCalculator calculator;
  private boolean isDuringAnimation;

  private ScreenStateChangeListener listener;

  public AdjustFullScreenLayout(Context context) {
    this(context, null);
  }

  public AdjustFullScreenLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AdjustFullScreenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    calculator = DisplayCalculator.with(this);
    calculator.setOnApplyWIndowInsetsListener(this);
  }

  public void setScreenStateChangeListener(ScreenStateChangeListener listener) {
    this.listener = listener;
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();
    //updatePadding();
  }

  @Override protected void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    //updatePadding();
  }

  @Override protected Parcelable onSaveInstanceState() {
    Parcelable savedState = super.onSaveInstanceState();
    AdjustFullScreenLayoutSavedState ss = new AdjustFullScreenLayoutSavedState(savedState);
    ss.screenState = currentScreenState.ordinal();
    return ss;
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    if (state instanceof AdjustFullScreenLayoutSavedState) {
      AdjustFullScreenLayoutSavedState ss = (AdjustFullScreenLayoutSavedState) state;
      super.onRestoreInstanceState(ss.getSuperState());
      this.lastScreenState = ScreenState.values()[ss.screenState];
      adjustState();
    }
  }

  public void toFullScreen() {
    Log.v("tag", "======== can get size: " + calculator.fixedSize());
    if (!calculator.fixedSize()) {
      calculator.log();
      lastScreenState = ScreenState.FULL;
      return;
    }
    ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
    final int left = getPaddingLeft();
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float fraction = (Float) animation.getAnimatedValue();
        int top = Math.round(calculator.getStatusBarHeight() * fraction);
        int right = Math.round(calculator.getNavigationBarWidth() * fraction);
        int bottom = Math.round(calculator.getNavigationBarHeight() * fraction);
        Log.v("tag", "=== f +++++ " + top + ", " + right + ", " + bottom);
        setPadding(left, top, right, bottom);
      }
    });
    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(Animator animation) {
        isDuringAnimation = true;
      }

      @Override public void onAnimationEnd(Animator animation) {
        isDuringAnimation = false;
        lastScreenState = currentScreenState;
      }

      @Override public void onAnimationCancel(Animator animation) {
        isDuringAnimation = false;
        lastScreenState = currentScreenState;
      }
    });
    animator.start();
    setCurrentScreenState(ScreenState.FULL);
    calculator.log();
  }

  public void toNormalScreen() {
    Log.v("tag", "======== can get size: " + calculator.fixedSize());
    if (!calculator.fixedSize()) {
      calculator.log();
      lastScreenState = ScreenState.NORMAL;
      return;
    }
    ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
    final int left = getPaddingLeft();
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float fraction = (Float) animation.getAnimatedValue();
        int top = Math.round(calculator.getStatusBarHeight() * fraction);
        int right = Math.round(calculator.getNavigationBarWidth() * fraction);
        int bottom = Math.round(calculator.getNavigationBarHeight() * fraction);
        Log.v("tag", "=== n +++++ " + top + ", " + right + ", " + bottom);
        setPadding(left, top, right, bottom);
      }
    });
    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(Animator animation) {
        isDuringAnimation = true;
      }

      @Override public void onAnimationEnd(Animator animation) {
        isDuringAnimation = false;
        lastScreenState = currentScreenState;
        requestLayout();
      }

      @Override public void onAnimationCancel(Animator animation) {
        isDuringAnimation = false;
        lastScreenState = currentScreenState;
      }
    });
    animator.start();
    setCurrentScreenState(ScreenState.NORMAL);
    calculator.log();
  }

  public boolean isFullScreen() {
    return currentScreenState == ScreenState.FULL;
  }

  private void updatePadding() {
    Log.v("tag", "=== updatePadding: " + currentScreenState);
    if (currentScreenState == ScreenState.NORMAL) {
      toNormalScreen();
      //setPadding(getPaddingLeft(), calculator.getStatusBarHeight(),
      //    calculator.getNavigationBarWidth(), calculator.getNavigationBarHeight());
    } else {
      toFullScreen();
      //setPadding(getPaddingLeft(), 0, 0, 0);
    }
  }

  @Override public void onWindowInsetsChanged(WindowInsetsCompat insets) {
    Log.v("tag", "=== currentScreenState ===== : " + currentScreenState);
    Log.v("tag", "=== lastScreenState ====== : " + lastScreenState);
    adjustState();
  }

  private void adjustState() {
    if (currentScreenState != lastScreenState && !isDuringAnimation) {
      if (lastScreenState == ScreenState.FULL) {
        Log.v("tag", "====== toFull ==========");
        toFullScreen();
      } else {
        Log.v("tag", "====== toNormal ==========");
        toNormalScreen();
      }
    }
  }

  private void setCurrentScreenState(ScreenState screenState) {
    currentScreenState = screenState;
    if (listener != null) {
      listener.onScreenStateChanged(screenState);
    }
  }

  public interface ScreenStateChangeListener {
    void onScreenStateChanged(ScreenState screenState);
  }

  public enum ScreenState {
    NORMAL,
    FULL
  }
}
