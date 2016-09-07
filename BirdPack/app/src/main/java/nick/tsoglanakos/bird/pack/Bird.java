package nick.tsoglanakos.bird.pack;




import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.ViewGroup;

public class Bird extends ViewGroup implements Runnable {

	private int posY, posX;
	private boolean goingDown = true;
	private GameActivity context;
	public static final int startingPositionY = 100;
	public int distance = 0;
	public static int recordDistance;
private int extraSleep = 0;
	private boolean isSleedRising = false;
	private final int maxSleep = 14;

	public Bird(GameActivity context) {
		super(context);
		this.context = (GameActivity) context;

		distance = 0;
		setBackgroundResource(R.drawable.bird2);
		setWillNotDraw(false);
		setX(this.context.getWindowManager().getDefaultDisplay().getWidth() / 30);
	}

	private void resetTubeAndEggCounter() {
		Tube.counterOfPassTube = 0;
		Egg.counterOfPassEgg = 0;
	}

	public float getY() {
		return posY;
	}

	public boolean isGoingDown() {
		return goingDown;
	}

	public void setY(float y) {
		// super.setY(y);

		this.posY = posY + (int) y;
		if (posY <= 0) {
			posY = 0;
		}
		try {
			ViewGroup parentView = (ViewGroup) Bird.this.getParent();
			if (parentView != null
					&& posY + getHeight() > parentView.getHeight()
					&& getHeight() > 0 && posY + getHeight() > 0) {
				// posY = startingPositionY;
				// gameOver = true;
				getGmPlayView().gameOver();

			}
			this.layout(posX, posY, posX + getWidth(), posY + getHeight());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public GamePlay getGmPlayView() {
		GamePlay gp = null;
		gp = (GamePlay) getParent();
		return gp;
	}

	public void setX(float x) {
		// super.setY(y);

		this.posX = posX + (int) x;
		if (posX <= 0) {
			posX = 0;
		}

		ViewGroup parentView = (ViewGroup) Bird.this.getParent();

		this.layout(posX, posY, posX + getWidth(), posY + getHeight());

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

	}

	public Rect getBounds() {
		int x = (int) getX() + getWidth() / 4, y = (int) getY() + 3
				* getHeight() / 10, w = (int) getX() + (int) getWidth()
				- getWidth() / 5, h = (int) getY() + getHeight() - getHeight()
				/ 4;
		Rect rec = new Rect(x, y, w, h);

		return rec;
	}
	public Rect getTopBounds() {
		int x = (int) getX() + getWidth() / 2, y = (int)getY()+getHeight()/5+getHeight()/25, w = x+getWidth()/6, h = (int) getY() + 3* getHeight() / 10;
		Rect rec = new Rect(x, y, w, h);

		return rec;
	}public Rect getBottomBounds() {
		int x = (int) getX() + getWidth() / 3+getWidth()/17, 
				y = (int) getY() + getHeight() - getHeight()/ 3,
				w = x+ getWidth() / 4, 
				h = y+ getHeight()/ 6- getHeight()/35;
		Rect rec = new Rect(x, y, w, h);

		return rec;
	}

	public int getDistance() {
		return distance;
	}

	public void start() {

		new Thread(this).start();

	}

	public void setGoingDown(final boolean goingDown) {
		if (goingDown) {
			isSleedRising = true;
		} else {

			isSleedRising = false;
			extraSleep=0;
			this.goingDown = goingDown;
		}
	}

	@Override
	public synchronized void run() {

		distance = 0;
		final GamePlay gp = (GamePlay) getParent();
		int counter = 0;
		
		while (true) {
			try {
				if(GameActivity.isExited){
					break;
				}
				if (extraSleep >= maxSleep) {
				//	Log.e("extraSleep >= maxSleep","extraSleep >= maxSleep");
					isSleedRising = false;
					goingDown = true;
				}
				if (isSleedRising) {
					extraSleep++;
			//		Log.e("isSleedRising","isSleedRising");
				} else {
					if (extraSleep > 0) {
					//	Log.e("not SleedRising","not SleedRising");
						extraSleep--;
					}
				}
//Log.e("extraSleep = ",Integer.toString(extraSleep));
//Log.e("maxSleep = ",Integer.toString(maxSleep));
				Thread.sleep(GamePlay.birdSleep + extraSleep);
				counter++;
				if (counter % 100 == 0) {
					distance++;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (GamePlay.isGameOver) {
				break;
			}
			context.runOnUiThread(new Thread() {
				public void run() {
					try {
						gp.setDistance(distance);
						gp.invalidate();
						gp.postInvalidate();
						// if (Egg.eggs.isEmpty()) {
						// gp.eggPointer.setY(getY());
						// }
					} catch (Exception e) {
					e.printStackTrace();
					}

					if (context.getGamePlay().hasSuperControll) {
						if (GamePlay.superControllDirection != null
								&& GamePlay.superControllDirection.equals("UP")) {
							setY(-1);
						} else if (GamePlay.superControllDirection != null
								&& GamePlay.superControllDirection
										.equals("DOWN")) {
							setY(1);
						}
					} else {
						if (goingDown && !GamePlay.isGameOver) {
							setY(1);
						} else if (!GamePlay.isGameOver) {
							setY(-1);
						}
					}
					Rect bound = getBounds();
					if (bound != null) {
						// Log.e("bird bound",bound.toString());
						try {
							for (Enemy enemy : GamePlay.enemies) {
								try {
									if (gp != null && enemy.intersect(bound)) {
										gp.gameOver();
										break;
									}if (gp != null && enemy.intersect(getTopBounds())) {
										gp.gameOver();
										break;
									}if (gp != null && enemy.intersect(getBottomBounds())) {
										gp.gameOver();
										break;
									}
								} catch (Exception e) {

								}

							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			});

		}
	}
}
