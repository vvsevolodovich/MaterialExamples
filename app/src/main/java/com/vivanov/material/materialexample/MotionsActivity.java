package com.vivanov.material.materialexample;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.View;
import com.vivanov.material.materialexample.views.RippleView;
import com.vivanov.material.materialexample.views.RippleView.OnRippleCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class MotionsActivity extends FragmentActivity {

	private List<ObjectAnimator> mAnimators = new ArrayList<>();

	private int currentAnimator = -1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_motions);

		final RippleView view = (RippleView) findViewById(R.id.motion_rectangle_ripple);
		createAnimators(view);
		view.setOnRippleCompleteListener(new OnRippleCompleteListener() {
			@Override
			public void onComplete(RippleView rippleView) {
				if (currentAnimator == 3) {
					currentAnimator = 0;
				} else {
					currentAnimator++;
				}
				mAnimators.get(currentAnimator).start();
			}
		});
	}

	public static void open(MainActivity mainActivity) {
		mainActivity.startActivity(new Intent(mainActivity, MotionsActivity.class));
	}

	private void createAnimators(View view) {

		final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

		final ObjectAnimator y = topBottom(view, displayMetrics);

		mAnimators.add(topBottom(view, displayMetrics));
		mAnimators.add(leftRight(view, displayMetrics));
		mAnimators.add(bottomTop(view, displayMetrics));
		mAnimators.add(rightLeft(view, displayMetrics));
	}

	private ObjectAnimator topBottom(View view, DisplayMetrics displayMetrics) {
		final ObjectAnimator topBottom = ObjectAnimator.ofFloat(view, "y", view.getY(), displayMetrics.heightPixels - 100)
				.setDuration(200);
		topBottom.setInterpolator(new FastOutSlowInInterpolator());
		return topBottom;
	}

	private ObjectAnimator leftRight(View view, DisplayMetrics displayMetrics) {
		final ObjectAnimator leftRight = ObjectAnimator.ofFloat(view, "x", view.getX(), displayMetrics.widthPixels - 100)
				.setDuration(200);
		leftRight.setInterpolator(new FastOutSlowInInterpolator());
		return leftRight;
	}

	private ObjectAnimator bottomTop(View view, DisplayMetrics displayMetrics) {
		final ObjectAnimator bottomTop = ObjectAnimator.ofFloat(view, "y", view.getY(), 0)
				.setDuration(200);
		bottomTop.setInterpolator(new FastOutSlowInInterpolator());
		return bottomTop;
	}

	private ObjectAnimator rightLeft(View view, DisplayMetrics displayMetrics) {
		final ObjectAnimator rightLeft = ObjectAnimator.ofFloat(view, "x", view.getX(), 0)
				.setDuration(200);
		rightLeft.setInterpolator(new FastOutSlowInInterpolator());
		return rightLeft;
	}
}
