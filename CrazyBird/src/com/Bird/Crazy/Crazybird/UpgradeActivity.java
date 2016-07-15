package com.Bird.Crazy.Crazybird;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class UpgradeActivity extends Activity {
	private RelativeLayout layout;
	private ScrollView scroll;
	private LinearLayout ll;

	public static final String updateGeneral = "Background Upgrades",
			upgradeBird = "Bird Upgrades";
	public static String curectSelectedUpgradeToWatch = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(GameActivity.oriantation);
		init();
		setContentView(layout);
		layout.addView(scroll);
		generateUpgradeMenuView();
	}

	private void generateUpgradeMenuView() {
		ll.removeAllViews();
		addView(updateGeneral);
		addView(upgradeBird);

	}

	/*
	 * private void generateUpgradeView() { ll.removeAllViews(); if
	 * (selectedUpgrade.equals(updateWeather)) {
	 * addUpgrade("",R.drawable.cloud); } if
	 * (selectedUpgrade.equals(upgradeLook)) {
	 * 
	 * } }
	 */

	public void addView(View view) {
		ll.addView(view);

	}

	/*
	 * public void addUpgrade(String title) { addUpgrade(title, -1);
	 * 
	 * }
	 */
	/*
	 * public void addUpgrade(String title, int imageID) { Button b = new
	 * Button(this); b.setText(title); if (imageID != -1) {
	 * b.setBackgroundResource(imageID); } ll.addView(b, new
	 * LinearLayout.LayoutParams(getWindowManager()
	 * .getDefaultDisplay().getWidth(), getWindowManager()
	 * .getDefaultDisplay().getHeight() / 3));
	 * 
	 * }
	 */
	public void addView(String title) {

		RoundButton b = new RoundButton(this);

		b.setText(title);
		Drawable drawable = null;
		drawable = layout.getBackground();
		if (drawable instanceof ColorDrawable) {
			int color = ((ColorDrawable) drawable).getColor();
			b.setBackgroundColor(color);
		} else {
			layout.setBackgroundColor(Color.WHITE);
		}
		b.setOnClickListener(new OnClickListener() {

		

			@Override
			public void onClick(View v) {
				final RoundButton b = (RoundButton) v;
				curectSelectedUpgradeToWatch = (String) b.getText();
				startActivity(new Intent(UpgradeActivity.this,
						UpgradeSelectedItemMenu.class));
				
			}

		});
		ll.addView(b, new LinearLayout.LayoutParams(getWindowManager()
				.getDefaultDisplay().getWidth(), getWindowManager()
				.getDefaultDisplay().getHeight() / 5));
		b.postInvalidate();

	}

	private void init() {
		ll = new LinearLayout(this);
		layout = new RelativeLayout(this);
		scroll = new ScrollView(this);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// GameMenuView view= new GameMenuView(this);

		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		ll.setOrientation(1);

		scroll.addView(ll);

	}

	public void onBackPressed() {
		finish();
		startActivity(new Intent(this, MenuActivity.class));
	}
}
