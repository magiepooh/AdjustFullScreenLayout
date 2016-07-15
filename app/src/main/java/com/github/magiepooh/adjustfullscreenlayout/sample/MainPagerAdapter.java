package com.github.magiepooh.adjustfullscreenlayout.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by magiepooh on 2016/07/15.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
  public MainPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    return MainFragment.newInstance(ContentResIds.values()[position].resId);
  }

  @Override public int getCount() {
    return ContentResIds.values().length;
  }

  private enum ContentResIds {
    PAGE_1(R.drawable.demo0_1),
    PAGE_2(R.drawable.demo0_2);
    public final int resId;

    ContentResIds(int resId) {
      this.resId = resId;
    }
  }
}
