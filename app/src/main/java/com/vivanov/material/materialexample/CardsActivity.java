package com.vivanov.material.materialexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class CardsActivity extends FragmentActivity {

	private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cards_activity);

		mRecyclerView = (RecyclerView) findViewById(R.id.chats_recycler);
		mRecyclerView.setAdapter(new CardsAdapter(this));
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
	}

	public static void show(Activity activity) {
		activity.startActivity(new Intent(activity, CardsActivity.class));
	}

	private static class CardsAdapter extends Adapter<CardViewHolder> {

		private Activity mActivity;

		public CardsAdapter(Activity activity) {
			mActivity = activity;
		}

		@Override
		public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			final Context context = parent.getContext();
			final View inflated = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
			final CardViewHolder cardViewHolder = new CardViewHolder(inflated);
			inflated.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final ImageView imageView = cardViewHolder.mImageView;
					final TextView label = cardViewHolder.mLabel;

					final Intent intent = new Intent(mActivity, ChatActivity.class);
					if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
						Pair avatarPair = new Pair<View, String>(imageView, context.getResources().getString(R.string.transition_chat_avatar));
						Pair namePair = new Pair<View, String>(label, context.getResources().getString(R.string.transition_chat_name));

						ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, avatarPair, namePair);
						ActivityCompat.startActivity(mActivity, intent, optionsCompat.toBundle());
					} else {
						mActivity.startActivity(intent);
					}
				}
			});

			return cardViewHolder;
		}

		@Override
		public void onBindViewHolder(CardViewHolder holder, int position) {
			holder.mLabel.setText(String.valueOf(position));
		}

		@Override
		public int getItemCount() {
			return 3;
		}
	}

	private static class CardViewHolder extends ViewHolder {

		private ImageView mImageView;
		private TextView mLabel;

		public CardViewHolder(View itemView) {
			super(itemView);
			mImageView = (ImageView) itemView.findViewById(R.id.card_avatar);
			mLabel = (TextView) itemView.findViewById(R.id.card_label);
		}
	}
}
