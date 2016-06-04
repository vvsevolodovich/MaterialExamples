package com.vivanov.material.materialexample;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

			@Override
			public void onCompleteDoubleTap(RippleView rippleView) {
				final boolean shrink = view.getWidth() == getResources().getDisplayMetrics().widthPixels;
				mAnimatorFactory.createResize(view, shrink).start();
			}
		});
	}

	public static void open(MainActivity mainActivity) {
		mainActivity.startActivity(new Intent(mainActivity, MotionsActivity.class));
	}

	private class AnimatorFactory {

		private DisplayMetrics dm;

		private int originalWidth;

		public AnimatorFactory(DisplayMetrics dm) {
			this.dm = dm;
		}

		Animator create(int step, View view) {
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

		Animator createResize(View view, boolean shrink) {
			if (shrink) {
				return createResizeAnimator(view, originalWidth);
			} else {
				originalWidth = view.getMeasuredWidth();
				return createResizeAnimator(view, dm.widthPixels);
			}
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

	private ValueAnimator createResizeAnimator(final View view, int targetWidth) {
		ValueAnimator anim = ValueAnimator.ofInt(view.getMeasuredWidth(), targetWidth);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				int val = (Integer) valueAnimator.getAnimatedValue();
				ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
				Log.d("MaterialExample", "Animated value = " + val);
				layoutParams.width = val;
				view.setLayoutParams(layoutParams);
			}
		});

		anim.setInterpolator(new FastOutSlowInInterpolator());
		anim.setDuration(DURATION);
		anim.start();

		return anim;
	}
}
