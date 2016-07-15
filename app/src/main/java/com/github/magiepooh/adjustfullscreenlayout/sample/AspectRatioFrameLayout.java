package com.github.magiepooh.adjustfullscreenlayout.sample;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by magiepooh on 2016/07/15.
 */
public class AspectRatioFrameLayout extends FrameLayout {

  private float aspectRatio;

  public AspectRatioFrameLayout(final Context context) {
    super(context);
    init(null);
  }

  public AspectRatioFrameLayout(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(context.obtainStyledAttributes(attrs, R.styleable.AspectRatioFrameLayout));
  }

  public AspectRatioFrameLayout(final Context context, final AttributeSet attrs,
      final int defStyle) {
    super(context, attrs, defStyle);
    init(context.obtainStyledAttributes(attrs, R.styleable.AspectRatioFrameLayout));
  }

  private void init(final TypedArray attrs) {
    if (attrs == null) {
      return;
    }
    aspectRatio = attrs.getFloat(R.styleable.AspectRatioFrameLayout_aspectRatio, 0f);
  }

  public float getAspectRatio() {
    return aspectRatio;
  }

  public void setAspectRatio(final float aspectRatio) {
    this.aspectRatio = aspectRatio;
    requestLayout();
  }

  @Override protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
    if (aspectRatio != 0f) {
      final int width = MeasureSpec.getSize(widthMeasureSpec);
      final int w = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
      final int h = MeasureSpec.makeMeasureSpec((int) (width / aspectRatio), MeasureSpec.EXACTLY);
      super.onMeasure(w, h);
      return;
    }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
