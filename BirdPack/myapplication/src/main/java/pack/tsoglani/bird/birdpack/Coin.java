package pack.tsoglani.bird.birdpack;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

public class Coin extends View implements Runnable {
	public static ArrayList<Coin> coins;

	private GameActivity context;
	private Bird bird;
	public static int money;
	private GamePlay gp;
	private boolean isRunning = false;
	private static int sleep = GamePlay.gameSleep;

	public Coin(GameActivity context) {
		super(context);
		init();
		sleep = GamePlay.gameSleep;
		if(gp!=null)
		gp = context.getGamePlay();
		setX(context.getWindowManager().getDefaultDisplay().getWidth());
		if (GamePlay.GeneraUpdates.equals(GamePlay.COIN)) {
			this.setBackgroundResource(R.drawable.coin1);
		} else {
			this.setBackgroundResource(R.drawable.coin3);
		}
		coins.add(this);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public void newGame() {
		if (GamePlay.GeneraUpdates.equals(GamePlay.COIN)) {
			this.setBackgroundResource(R.drawable.coin1);
		} else {
			this.setBackgroundResource(R.drawable.coin3);
		}
		setX(5 * context.getGamePlay().getWidth() / 4);
		coins.add(this);
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setGamePlay(GamePlay gp) {
		this.gp = gp;
	}

	public void setBird(Bird bird) {
		this.bird = bird;
	}

	private void init() {
		coins = new ArrayList<Coin>() {
			public boolean add(Coin c) {
				if (coins.contains(c)) {
					return false;
				}
				return super.add(c);

			}
		};
	}



	public Rect getBounds() {
		Rect rec = new Rect((int) getX(), (int) getY(), (int) getX()
				+ getWidth(), (int) getY() + getHeight());

		if (rec.width() == 0 || rec.height() == 0) {
			return null;
		}
		return rec;
	}

	@Override
	public void run() {
		isRunning = true;
		while (true) {
			try {
if(GameActivity.isExited){
	break;
}
				Thread.sleep(sleep);
				try {
					GameActivity act = (GameActivity) getContext();
				} catch (Exception e) {
					continue;
				}
				if (getBounds() == null) {
					continue;
				}

				if (getX() + getWidth() <= 0 || GamePlay.isGameOver) {
					break;
				}
				try {
					for (Enemy en : GamePlay.enemies) {
						if (en instanceof Tube) {
							if (en.intersect(getBounds())) {
								break;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (bird != null && bird.getBounds().intersect(getBounds())) {

					money++;
					GamePlay.totalMoney++;
					gp.setCoinsCollected(money);
					try {

						context.runOnUiThread(new Thread() {
							public void run() {
								if(GameActivity.isExited){
									return;
								}
								if (gp != null) {
									MainActivity.loadSound("coin", context);
									gp.removeView(Coin.this);
								}

							}
						});

						
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}


				context.runOnUiThread(new Thread() {
					public void run() {
						setX(getX() - 1);
					}
				});
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			context.runOnUiThread(new Thread() {
				public void run() {
					if (gp != null) {
						Coin.this.setX(gp.getWidth());

						gp.removeView(Coin.this);
					}
				}
			});
			coins.remove(Coin.this);
			isRunning = false;
		} catch (Exception e) {
		}
	}
}
