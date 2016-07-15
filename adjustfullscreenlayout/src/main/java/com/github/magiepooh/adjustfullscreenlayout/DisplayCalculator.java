package com.github.magiepooh.adjustfullscreenlayout;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;

/**
 * Created by magiepooh on 2016/07/15.
 */
public class DisplayCalculator {
  private static final String TAG = DisplayCalculator.class.getSimpleName();

  private final Display display;

  private DisplayCalculator(Display display) {
    this.display = display;
  }

  public static DisplayCalculator with(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    return new DisplayCalculator(wm.getDefaultDisplay());
  }

  public int getNavigationBarWidth() {
    return getRealSize().x - getDisplaySize().x;
  }

  public int getNavigationBarHeight() {
    return getRealSize().y - getDisplaySize().y;
  }

  public int getStatusBarHeight() {
    Point smallSize = new Point();
    Point largeSize = new Point();
    display.getCurrentSizeRange(smallSize, largeSize);
    Point displaySize = getDisplaySize();
    if (displaySize.x > displaySize.y) {
      return displaySize.y - smallSize.y;
    } else {
      return displaySize.y - largeSize.y;
    }
  }

  private Point getDisplaySize() {
    Point point = new Point();
    display.getSize(point);
    return point;
  }

  private Point getRealSize() {
    Point realSize = new Point();
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
      try {
        Method getRawWidth = Display.class.getMethod("getRawWidth");
        Method getRawHeight = Display.class.getMethod("getRawHeight");
        int width = (Integer) getRawWidth.invoke(display);
        int height = (Integer) getRawHeight.invoke(display);
        realSize.set(width, height);
      } catch (Exception e) {
        Log.e(TAG, "cannot get real size of display: " + e);
      }
    } else {
      display.getRealSize(realSize);
    }
    return realSize;
  }
}
