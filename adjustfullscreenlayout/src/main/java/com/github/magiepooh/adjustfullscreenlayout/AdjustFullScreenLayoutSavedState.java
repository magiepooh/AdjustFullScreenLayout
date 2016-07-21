package com.github.magiepooh.adjustfullscreenlayout;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by magiepooh on 2016/07/20.
 */

public class AdjustFullScreenLayoutSavedState extends View.BaseSavedState {

  public int screenState;

  public AdjustFullScreenLayoutSavedState(Parcel source) {
    super(source);
    this.screenState = source.readInt();
  }

  public AdjustFullScreenLayoutSavedState(Parcelable superState) {
    super(superState);
  }

  @Override public void writeToParcel(Parcel out, int flags) {
    super.writeToParcel(out, flags);
    out.writeInt(screenState);
  }

  public static final Creator<AdjustFullScreenLayoutSavedState> CREATOR =
      new Creator<AdjustFullScreenLayoutSavedState>() {
        public AdjustFullScreenLayoutSavedState createFromParcel(Parcel in) {
          return new AdjustFullScreenLayoutSavedState(in);
        }

        public AdjustFullScreenLayoutSavedState[] newArray(int size) {
          return new AdjustFullScreenLayoutSavedState[size];
        }
      };
}
