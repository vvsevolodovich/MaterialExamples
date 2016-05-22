package com.vivanov.material.materialexample;


import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import com.vivanov.material.materialexample.views.ScaleCircleView;

public class SecondActivity extends FragmentActivity {

	private static final long TIME_OPEN_ANIMATION = 300;

	public static void openAnimated(final Activity fromActivity, final View original, int colorId) {
		final ViewGroup container = (ViewGroup) fromActivity.findViewById(android.R.id.content);

		final ViewGroup.LayoutParams layoutParams =
				new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		final ScaleCircleView scaleCircleView = new ScaleCircleView(container.getContext());

		scaleCircleView.setLayoutParams(layoutParams);
		container.addView(scaleCircleView);
		final int w = container.getWidth(), h = container.getHeight();
		final int size = (int) (((w > h) ? w : h) * 1.3f);

		float[] coords = getCoordinates(original);

		scaleCircleView.startAnimation(size, coords, fromActivity.getResources().getColor(colorId), new Runnable() {
			@Override
			public void run() {
				final Intent intent = new Intent(fromActivity, SecondActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				fromActivity.startActivity(intent);
				//container.removeView(scaleCircleView);
			}
		});

	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);

		final ImageView animation = (ImageView) findViewById(R.id.second_activity_space_animation);

		final Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		int height = size.y;
		ObjectAnimator scaleAnimatorY = ObjectAnimator.ofFloat(animation, "y", 0.0f, -height);
		scaleAnimatorY.setDuration(TIME_OPEN_ANIMATION);

		ValueAnimator animColor = ObjectAnimator.ofInt(animation, "backgroundColor",
				getResources().getColor(R.color.colorAccent),
				getResources().getColor(R.color.colorPrimary));
		animColor.setDuration(TIME_OPEN_ANIMATION);
		animColor.setEvaluator(new ArgbEvaluator());

		AnimatorSet openDial = new AnimatorSet();
		openDial.playTogether(animColor,  scaleAnimatorY);
		openDial.setStartDelay(300);
		openDial.start();
	}

	private static float[] getCoordinates(View original) {
		Rect rect = new Rect();
		original.getGlobalVisibleRect(rect);
		return new float[]{rect.centerX(), rect.centerY()};
	}
}
