package com.vivanov.material.materialexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.vivanov.material.materialexample.views.RippleView;
import com.vivanov.material.materialexample.views.RippleView.OnRippleCompleteListener;
import com.vivanov.material.materialexample.views.ScaleCircleView;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("TAG", "Test");
				SecondActivity.openAnimated(MainActivity.this, v, R.color.colorAccent);
			}
		});

		final RippleView button = (RippleView) findViewById(R.id.button_ripple);
		button.setOnRippleCompleteListener(new OnRippleCompleteListener() {
			@Override
			public void onComplete(RippleView rippleView) {
				CardsActivity.show(MainActivity.this);
			}

			@Override
			public void onCompleteDoubleTap(RippleView rippleView) {

			}
		});

		final View motions = findViewById(R.id.motions_button);
		motions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MotionsActivity.open(MainActivity.this);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		final ViewGroup container = (ViewGroup) findViewById(android.R.id.content);
		int childCount = container.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childAt = container.getChildAt(i);
			if (childAt instanceof ScaleCircleView) {
				container.removeView(childAt);
				break;
			}
		}
	}
}
