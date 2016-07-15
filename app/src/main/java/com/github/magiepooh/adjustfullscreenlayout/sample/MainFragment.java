package com.github.magiepooh.adjustfullscreenlayout.sample;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by magiepooh on 2016/07/15.
 */
public class MainFragment extends Fragment {

  private static final String KEY_CONTENT = "key_content";

  public static MainFragment newInstance(@DrawableRes int imageResId) {
    MainFragment f = new MainFragment();
    Bundle args = new Bundle();
    args.putInt(KEY_CONTENT, imageResId);
    f.setArguments(args);
    return f;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_main, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ImageView imageView = (ImageView) view.findViewById(R.id.page_content);
    int imageResId = getArguments().getInt(KEY_CONTENT);
    imageView.setImageResource(imageResId);
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (getActivity() instanceof OnClickImageCallback) {
          ((OnClickImageCallback) getActivity()).onClickImage();
        }
      }
    });
  }
}
