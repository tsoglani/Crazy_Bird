package pack.tsoglani.bird.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
//		setRequestedOrientation(GameActivity.oriantation);
		init();
		setContentView(layout);
		layout.addView(scroll);
		generateUpgradeMenuView();
		scroll.setOnTouchListener(new OnSwipeTouchListener(getApplication()));

		layout.setOnTouchListener(new OnSwipeTouchListener(getApplication()));
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
		ll.setOrientation(LinearLayout.VERTICAL);

		scroll.addView(ll);

	}

	public void onBackPressed() {
		finish();
		startActivity(new Intent(this, MainActivity.class));
	}
	public class OnSwipeTouchListener implements View.OnTouchListener {
		private final GestureDetector gestureDetector;

		public OnSwipeTouchListener (Context ctx){
			gestureDetector = new GestureDetector(ctx, new GestureListener());
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return gestureDetector.onTouchEvent(event);
		}

		private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

			private static final int SWIPE_THRESHOLD = 100;
			private static final int SWIPE_VELOCITY_THRESHOLD = 100;

//			@Override
//			public boolean onDown(MotionEvent e) {
//				return true;
//			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				boolean result = false;
				try {
					float diffY = e2.getY() - e1.getY();
					float diffX = e2.getX() - e1.getX();
					if (Math.abs(diffX) > Math.abs(diffY)) {
						if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
							if (diffX > 0) {
								onSwipeRight();
							} else {
								onSwipeLeft();
							}
						}
						result = true;
					}
					else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffY > 0) {
							onSwipeBottom();
						} else {
							onSwipeTop();
						}
					}
					result = true;

				} catch (Exception exception) {
					exception.printStackTrace();
				}
				return result;
			}
		}

		public void onSwipeRight() {
			onBackPressed();
		}


		public void onSwipeLeft() {
			onBackPressed();
		}

		public void onSwipeTop() {
		}

		public void onSwipeBottom() {
		}
	}

}
