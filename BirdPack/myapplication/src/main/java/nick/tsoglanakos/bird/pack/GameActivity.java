package nick.tsoglanakos.bird.pack;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class GameActivity extends Activity {
	private RelativeLayout layout;
//	public static int oriantation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	public static Point Size = new Point();
	public  static boolean isExited=false;
	private PowerManager.WakeLock screenLock;
public static Activity activity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isExited=false;
//		setRequestedOrientation(oriantation);
		activity=this;
		layout = new RelativeLayout(this);
		this.getWindowManager().getDefaultDisplay().getRealSize(Size);
		// RelativeLayout relative = new RelativeLayout(this);
		// setContentView(R.layout.activity_main);
		this.setContentView(layout);

		addNewGameView();

	}

	private GamePlay gameView;

	public void addNewGameView() {

		layout.removeAllViews();
		gameView = new GamePlay(this);
		layout.addView(gameView);
		new Thread(gameView).start();
		layout.invalidate();
		layout.postInvalidate();
	}

	public GamePlay getGamePlay() {
		return gameView;
	}

	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// if (GamePlay.isGameOver) {
		// menu.setHeaderTitle("New Game");
		// menu.add(0, v.getId(), 0, "Action 1");
		// }
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// if(item.getTitle()=="Action 1"){
		//
		// }
		return super.onContextItemSelected(item);

	}

	@Override
	protected void onStop() {
		super.onStop();
		isExited=true;
		screenLock.release();
		GamePlay.bird=null;
		GamePlay.enemies.clear();

	}
	@Override
	protected void onResume() {
		super.onResume();
		isExited=false;
		screenLock =    ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
		screenLock.acquire();
	}

	public RelativeLayout getLayout() {
		return layout;
	}

	@Override
	public void onBackPressed() {

		// Toast.makeText(this, " Thank you for playing",
		// Toast.LENGTH_SHORT).show();
		GamePlay.isGameOver = true;
		// Log.e("onBackPressed", "onBackPressed");
		// super.onBackPressed();
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
