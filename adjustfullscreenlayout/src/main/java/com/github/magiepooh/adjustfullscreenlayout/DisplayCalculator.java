package com.github.magiepooh.adjustfullscreenlayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by magiepooh on 2016/07/15.
 */
public class DisplayCalculator {
  private static final String TAG = DisplayCalculator.class.getSimpleName();

  private final Display display;
  private WindowInsetsCompat mLastInsets;
  private boolean mDrawStatusBarBackground;

  private WindowInsetsChangeListener listener;

  private DisplayCalculator(Display display, View view) {
    this.display = display;
    ViewCompat.setOnApplyWindowInsetsListener(view, new ApplyInsetsListener());
  }

  public static DisplayCalculator with(View view) {
    WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
    return new DisplayCalculator(wm.getDefaultDisplay(), view);
  }

  public void setOnApplyWIndowInsetsListener(WindowInsetsChangeListener listener) {
    this.listener = listener;
  }

  public void log() {
    Log.v(TAG, "=== mLastInsets.getSystemWindowInsetBottom(): " + (mLastInsets == null ? "null"
        : mLastInsets.getSystemWindowInsetBottom()));
    Log.v(TAG, "=== mLastInsets.getSystemWindowInsetTop(): " + (mLastInsets == null ? "null"
        : mLastInsets.getSystemWindowInsetTop()));
    Log.v(TAG, "=== mLastInsets.getStableInsetLeft(): " + (mLastInsets == null ? "null"
        : mLastInsets.getStableInsetLeft()));
    Log.v(TAG, "=== mLastInsets.getStableInsetRight(): " + (mLastInsets == null ? "null"
        : mLastInsets.getStableInsetRight()));

    Log.v(TAG, "=== Nav H: " + getNavigationBarHeight() + " W: " + getNavigationBarWidth());
    Log.v(TAG, "=== Status H: " + getStatusBarHeight());
  }

  public boolean fixedSize() {
    return mLastInsets != null;
  }

  public int getNavigationBarWidth() {
    return mLastInsets == null ? 0 : mLastInsets.getSystemWindowInsetRight();
  }

  public int getNavigationBarHeight() {
    return mLastInsets == null ? 0 : mLastInsets.getSystemWindowInsetBottom();
  }

  public int getStatusBarHeight() {
    return mLastInsets == null ? 0 : mLastInsets.getSystemWindowInsetTop();
  }

  private class ApplyInsetsListener implements android.support.v4.view.OnApplyWindowInsetsListener {
    @Override public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
      Log.v(TAG, "============ insets: " + insets);
      setWindowInsets(insets);
      return insets;
    }
  }

  private void setWindowInsets(WindowInsetsCompat insets) {
    if (mLastInsets != insets) {
      mLastInsets = insets;
      mDrawStatusBarBackground = insets != null && insets.getSystemWindowInsetTop() > 0;
      listener.onWindowInsetsChanged(insets);
    }
  }

  public interface WindowInsetsChangeListener {
    void onWindowInsetsChanged(WindowInsetsCompat insets);
  }
}
