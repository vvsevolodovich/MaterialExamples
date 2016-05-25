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

public class MotionsActivity extends FragmentActivity {

	private static final int DURATION = 500;
	private AnimatorFactory mAnimatorFactory;

	private int currentAnimator = -1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_motions);

		final RippleView view = (RippleView) findViewById(R.id.motion_rectangle_ripple);
		mAnimatorFactory = new AnimatorFactory(getResources().getDisplayMetrics());
		view.setOnRippleCompleteListener(new OnRippleCompleteListener() {
			@Override
			public void onComplete(RippleView rippleView) {
				if (currentAnimator == 3) {
					currentAnimator = 0;
				} else {
					currentAnimator++;
				}
				mAnimatorFactory.create(currentAnimator, view).start();
			}
		});
	}

	public static void open(MainActivity mainActivity) {
		mainActivity.startActivity(new Intent(mainActivity, MotionsActivity.class));
	}

	private class AnimatorFactory {

		private DisplayMetrics dm;

		public AnimatorFactory(DisplayMetrics dm) {
			this.dm = dm;
		}

		ObjectAnimator create(int step, View view) {
			switch (step) {
				case 0:
					return topBottom(view, dm);
				case 1:
					return leftRight(view, dm);
				case 2:
					return bottomTop(view, dm);
				case 3:
					return rightLeft(view, dm);
			}
			return null;
		}
	}

	private ObjectAnimator topBottom(View view, DisplayMetrics displayMetrics) {
		final ObjectAnimator topBottom = ObjectAnimator.ofFloat(view, "y", view.getY(), displayMetrics.heightPixels - view.getHeight())
				.setDuration(DURATION);
		topBottom.setInterpolator(new FastOutSlowInInterpolator());
		return topBottom;
	}

	private ObjectAnimator leftRight(View view, DisplayMetrics displayMetrics) {
		final ObjectAnimator leftRight = ObjectAnimator.ofFloat(view, "x", view.getX(), displayMetrics.widthPixels - view.getWidth())
				.setDuration(DURATION);
		leftRight.setInterpolator(new FastOutSlowInInterpolator());
		return leftRight;
	}

	private ObjectAnimator bottomTop(View view, DisplayMetrics displayMetrics) {
		final ObjectAnimator bottomTop = ObjectAnimator.ofFloat(view, "y", view.getY(), 0)
				.setDuration(DURATION);
		bottomTop.setInterpolator(new FastOutSlowInInterpolator());
		return bottomTop;
	}

	private ObjectAnimator rightLeft(View view, DisplayMetrics displayMetrics) {
		final ObjectAnimator rightLeft = ObjectAnimator.ofFloat(view, "x", view.getX(), 0)
				.setDuration(DURATION);
		rightLeft.setInterpolator(new FastOutSlowInInterpolator());
		return rightLeft;
	}
}
