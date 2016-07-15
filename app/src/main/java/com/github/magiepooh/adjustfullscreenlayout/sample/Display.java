package com.github.magiepooh.adjustfullscreenlayout.sample;

import android.os.Build;
import android.view.View;
import android.view.Window;

/**
 * Created by magiepooh on 2016/07/15.
 */
public class Display {

  public static void hideSystemUi(Window window) {
    int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      options = options ^ View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    } else {
      options = options | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    }
    window.getDecorView().setSystemUiVisibility(options);
  }

  public static void showSystemUi(Window window) {
    window.getDecorView()
        .setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
  }
}
