package nick.tsoglanakos.bird.pack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoActivity extends Activity {
	private LinearLayout layout;
	private TextView txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();

		setContentView(layout);
		layout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
		layout.setOnTouchListener(new OnSwipeTouchListener(getApplication()));
		generateText();
		addComponent();
	}

	private void generateText() {

		txt.setText(" The purpose of this game is to control the bird and try to avoid the tubes and the eggs that come on to you, at the same time you have to complete game's Achievements, also you can collect coins, and with them, unlock  game's upgrades .    \n");
		txt.append("\n\nThanks for playing \n I hope to enjoy it\n\n");
		txt.append("The Game Created By \nNikos Gaitanis ");
	}

	private void init() {
		layout = new LinearLayout(this);
		txt = new TextView(this);
	}

	private void addComponent() {
		layout.addView(txt);

	}



	public void onBackPressed() {


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

//		@Override
//		public boolean onDown(MotionEvent e) {
//			return true;
//		}

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
