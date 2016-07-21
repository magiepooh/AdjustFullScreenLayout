package com.github.magiepooh.adjustfullscreenlayout.sample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.github.magiepooh.adjustfullscreenlayout.AdjustFullScreenLayout;

/**
 * Created by magiepooh on 2016/07/15.
 */
public class MainActivity extends AppCompatActivity implements OnClickImageCallback {

  private boolean isFullScreen;

  private int horizontalMargin;
  private int verticalMargin;
  private int pageMargin;

  private AdjustFullScreenLayout adjustFullScreenLayout;
  private ViewPager pager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    pager = (ViewPager) findViewById(R.id.main_pager);
    adjustFullScreenLayout = (AdjustFullScreenLayout) findViewById(R.id.adjust_fullscreen_layout);

    assert pager != null;
    assert adjustFullScreenLayout != null;

    Display.showSystemUi(getWindow());

    horizontalMargin = getResources().getDimensionPixelSize(R.dimen.pager_horizontal_margin);
    verticalMargin = getResources().getDimensionPixelSize(R.dimen.pager_vertical_margin);
    pageMargin = getResources().getDimensionPixelSize(R.dimen.pager_page_margin);

    pager.setPadding(-horizontalMargin, verticalMargin, -horizontalMargin, verticalMargin);
    pager.setPageMargin(-pageMargin);
    pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

    adjustFullScreenLayout.toFullScreen();
    adjustFullScreenLayout.setScreenStateChangeListener(
        new AdjustFullScreenLayout.ScreenStateChangeListener() {
          @Override
          public void onScreenStateChanged(AdjustFullScreenLayout.ScreenState screenState) {
            if (screenState == AdjustFullScreenLayout.ScreenState.FULL) {
              if (!isFullScreen) {
                //toFullScreen();
              }
            } else {
              if (isFullScreen) {
                //toNormalScreen();
              }
            }
          }
        });
  }

  @Override public void onClickImage() {
    isFullScreen = !isFullScreen;
    if (isFullScreen) {
      toFullScreen();
    } else {
      toNormalScreen();
    }
  }

  private void toNormalScreen() {
    final ValueAnimator pagerAnim = ValueAnimator.ofFloat(0.f, 1.f);
    pagerAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float fraction = (Float) animation.getAnimatedValue();
        int left, top, right, bottom;
        left = right = pager.getPaddingLeft();
        top = Math.round(verticalMargin * fraction);
        bottom = Math.round(verticalMargin * fraction);
        pager.setPadding(left, top, right, bottom);
      }
    });
    pagerAnim.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        pager.requestLayout();
      }
    });
    pagerAnim.start();
    adjustFullScreenLayout.toNormalScreen();
    Display.showSystemUi(getWindow());
  }

  private void toFullScreen() {
    final ValueAnimator pagerAnim = ValueAnimator.ofFloat(1.f, 0.f);
    pagerAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float fraction = (Float) animation.getAnimatedValue();
        int left, top, right, bottom;
        left = right = pager.getPaddingLeft();
        top = Math.round(verticalMargin * fraction);
        bottom = Math.round(verticalMargin * fraction);
        pager.setPadding(left, top, right, bottom);
      }
    });
    pagerAnim.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        pager.requestLayout();
      }
    });
    pagerAnim.start();
    adjustFullScreenLayout.toFullScreen();
    Display.hideSystemUi(getWindow());
  }
}
