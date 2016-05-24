package com.vivanov.material.materialexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.vivanov.material.materialexample.views.RippleView;
import com.vivanov.material.materialexample.views.RippleView.OnRippleCompleteListener;

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
		});

		final View motions = findViewById(R.id.motions_button);
		motions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MotionsActivity.open(MainActivity.this);
			}
		});
	}
}
