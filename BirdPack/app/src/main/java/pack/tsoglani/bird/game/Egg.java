package pack.tsoglani.bird.game;

import java.util.ArrayList;


import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class Egg extends View implements Runnable, Enemy {
	private GameActivity context;
	public static int counterOfPassEgg = 0;
	public static ArrayList<Egg> eggs = new ArrayList<Egg>();
	private boolean isRunning = false;

	public Egg(GameActivity context) {
		super(context);
		this.context = context;
		GamePlay.enemies.add(this);
		setBackgroundResource(R.drawable.egg0);
		eggs.add(this);

		// TODO Auto-generated constructor stub
	}

	public Rect getBounds() {
		Rect rec = new Rect();
		rec = new Rect((int) getX() + getWidth() / 10, (int) getY(),
				(int) getX() + getWidth(), (int) getY() + getHeight());
		if(rec.width()==0||rec.height()==0){
			return null;
		}
		
		return rec;
	}

	@Override
	public boolean intersect(Rect rec) {
		if (getBounds().intersect(rec)) {
			return true;
		}
		return false;
	}

	private void onEggPointer() {
		context.runOnUiThread(new Thread() {
			public void run() {
				context.getGamePlay().visibleEggPointer();
			}
		});

	}

	private void offEggPointer() {
		context.runOnUiThread(new Thread() {
			public void run() {
				context.getGamePlay().invisibleEggPointer();
			}
		});

	}

	public void newEgg() {
		GamePlay.enemies.add(this);
		eggs.add(this);
		context.runOnUiThread(new Thread() {
			public void run() {
				setX(2 * context.getGamePlay().getWidth());
			}
		});

	}

	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void run() {
		Log.e("egg run", "run egg");
		isRunning = true;
		context.runOnUiThread(new Thread() {
			public void run() {
				context.getGamePlay().eggPointer.setY(getY());
			}
		});

		boolean isVisibleEggPointer = true;
		while (true) {
			if(GameActivity.isExited){
				break;
			}
			try {
				Thread.sleep(GamePlay.eggSleep);
				if (getBounds() == null) {
					continue;
				}
				
				if (!context.getGamePlay().getBounds()
						.contains(this.getBounds())) {
					onEggPointer();
					if (isVisibleEggPointer) {
						context.getGamePlay().setEggPointerColor(
								context.getResources().getColor(
										R.color.holo_red_dark));
					} else {
						context.getGamePlay().setEggPointerColor(
								context.getResources().getColor(
										R.color.holo_red_light));
					}
					isVisibleEggPointer = !isVisibleEggPointer;
				} else {
					offEggPointer();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			context.runOnUiThread(new Thread() {
				public void run() {
					setX(getX() - 2);
				}
			});

			if (getX() + getWidth() < 0 || GamePlay.isGameOver) {
				Log.e("getWidth egg", Float.toString(getX()));
				break;
			}
		}

		GamePlay.enemies.remove(this);
		counterOfPassEgg++;
		context.runOnUiThread(new Thread() {
			public void run() {
				try {
					GamePlay gp = context.getGamePlay();
					gp.removeView(Egg.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GamePlay.enemies.remove(this);
		eggs.remove(this);
		isRunning = false;
		Log.e("egg finish", "finish egg");
	}

}
