package pack.tsoglani.bird.game;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


public class GameActivity extends Activity {
	private RelativeLayout layout;
	public static int oriantation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
	public static Point Size = new Point();
public static Activity activity;
	private PowerManager.WakeLock screenLock;
	public  static boolean isExited=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(oriantation);
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
		startActivity(new Intent(this, MenuActivity.class));
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

}
