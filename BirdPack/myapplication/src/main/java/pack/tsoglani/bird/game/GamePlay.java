package pack.tsoglani.bird.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GamePlay extends ViewGroup implements Runnable {
	protected static Bird bird;
	public static ArrayList<Enemy> enemies;
	public static int width, height;
	private GameActivity context;
	public static boolean isGameOver = false;

	private Boom boom;
	private Button newGameButton, menuButton, exitButton;

	private String distance = null, coinsCollected = null;
	public static int totalMoney;
	public boolean hasBetterControll = false, hasSuperControll = false;
	public static String superControllDirection = "";
	public static final String Clasic = "clasic", CLOUD_1 = "Cloudy Day",
			RAINING = "Raining Day",SNOW = "Snowing Day", RAINING_NIGHT = "Raining Night",
			Faster = "Faster", DelayEnemies = "Delay Enemies",
			COIN = "New Coin", SMALLER_JUMP="Smaller Jump",BETTER_CONTROL = "Super Control",
			Master_CONTROL = "Master Control";
	public static String GeneraUpdates = Clasic;
//	public View eggPointer;
	// public static String BirdUpdates = Clasic;
	public static ArrayList<String> birdUpdatesUsing = new ArrayList<String>();
	private static final int defGameSleep = 3, defBirdSleep = 2,
			defEggSleep = 2;
	private static int difBirdSleepToFall = 230;
	public static int gameSleep = defGameSleep, birdSleep = defBirdSleep,
			eggSleep = defEggSleep, birdSleepToFall = difBirdSleepToFall;
	public static int eggRation;
	private int tubeRation = 800, eggPointerColor = 0,
			coinRation = tubeRation;
	private Egg[] eggs = new Egg[3];// egg1, egg2;
//	private Coin[] coins = new Coin[3];// coin1, coin2, coin3;

	public GamePlay(final GameActivity context) {
		super(context);
		this.context = context;

		if (GeneraUpdates.equals(CLOUD_1) || GeneraUpdates.equals(RAINING)
				|| GeneraUpdates.equals(RAINING_NIGHT)|| GeneraUpdates.equals(SNOW)) {
			addView(new Animation(context));
		}

		// Log.e("GeneraUpdates in game play = ", GeneraUpdates);
		/*
		 * setLayerType(View.LAYER_TYPE_SOFTWARE, null); if
		 * (GeneraUpdates.equals(CLOUD_1)) { movie =
		 * Movie.decodeStream(getResources().getAssets().open( "cloud.gif")); //
		 * addBackGround(R.drawable.cloud); } else if
		 * (GeneraUpdates.equals(RAINING)) { movie =
		 * Movie.decodeStream(getResources().getAssets().open( "rain0.gif")); //
		 * addBackGround(R.drawable.rain0); //
		 * Log.e("GeneraUpdates.equals(RAINING) = ", GeneraUpdates); } else if
		 * (GeneraUpdates.equals(RAINING_NIGHT)) { movie =
		 * Movie.decodeStream(getResources().getAssets().open( "rain3.gif")); //
		 * addBackGround(R.drawable.rain3); } } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		gameSleep = defGameSleep;
		birdSleep = defBirdSleep;
		eggSleep = defEggSleep;
		birdSleepToFall = difBirdSleepToFall;
		Egg.counterOfPassEgg = 0;
		Tube.counterOfPassTube = 0;
		// if
		// (BirdUpdates.equals(BETTER_CONTROL)||birdUpdatesUsing.contains(BETTER_CONTROL))
		// {
		birdUpdatesUsing.clear();
		birdUpdatesUsing.add(Master_CONTROL);
		birdUpdatesUsing.add(DelayEnemies);
		birdUpdatesUsing.add(DelayEnemies);

		if (birdUpdatesUsing.contains(Master_CONTROL)) {
			hasSuperControll = true;
		} else if (birdUpdatesUsing.contains(BETTER_CONTROL)) {
			hasBetterControll = true;
		}
		
		if (birdUpdatesUsing.contains(SMALLER_JUMP)){
			birdSleepToFall=150;
		}
		// if (BirdUpdates.equals(Faster)||birdUpdatesUsing.contains(Faster)) {
		if (birdUpdatesUsing.contains(Faster)) {
			gameSleep = (int) (gameSleep / 2);
			birdSleep = (int) (birdSleep / 2);
			eggSleep = (int) (eggSleep / 1.1);
			birdSleepToFall = (int) (birdSleepToFall / 2);
		}
		// if
		// (BirdUpdates.equals(DelayEnemies)||birdUpdatesUsing.contains(DelayEnemies))
		// {
		if (birdUpdatesUsing.contains(DelayEnemies)) {
			gameSleep = (int) (5.0 * gameSleep / 2);
			eggSleep = (int) (5.0 * eggSleep / 2);
		}
//		eggPointer = new View(context) {
//
//			public void onDraw(Canvas canvas) {
//				super.onDraw(canvas);
//				Paint paint = new Paint();
//
//				if (eggPointerColor == 0) {
//					paint.setColor(Color.RED);
//				} else {
//					paint.setColor(eggPointerColor);
//				}
//				canvas.drawCircle(getWidth() / 2, getHeight() / 2,
//						getWidth() / 2, paint);
//				paint.setColor(Color.WHITE);
//				canvas.drawCircle(getWidth() / 2, getHeight() / 2,
//						getWidth() / 2 - 3, paint);
//
//				if (eggPointerColor == 0) {
//					paint.setColor(Color.RED);
//				} else {
//					paint.setColor(eggPointerColor);
//				}
//				paint.setTextSize(getWidth() / 2);
//				canvas.drawText("X", getWidth() / 3, 2 * getHeight() / 3, paint);
//
//			}
//		};
		init();
//		for (int i = 0; i < coins.length; i++) {
//			coins[i] = new Coin(context);
//		}
		// coin1 = new Coin(context);
		// coin2 = new Coin(context);
		// coin3 = new Coin(context);
		setBackgroundColor(context.getResources().getColor(R.color.Sky));
		setWillNotDraw(false);
		boom = new Boom(context);
		boom.setVisibility(INVISIBLE);
		width = getWidth();
		height = getHeight();

		setOnTouchListener(touchListener);
		// new Thread(bird).start();
		bird.start();
		WindowManager.LayoutParams params;
		newGameButton = new Button(context);
		menuButton = new Button(context);
		exitButton = new Button(context);
		newGameButton.setText("new Game");

		gameOverMenuButtonsListeners();
		newGameButton.setBackgroundResource(R.drawable.play1);
		exitButton.setBackgroundResource(R.drawable.exit1);
		menuButton.setBackgroundResource(R.drawable.menu1);
	}

	public void addBackGround(final int ID) {
		final GifView gif_view = new GifView(context);
		// gif_view.setBackgroundColor(this.getResources().getColor(R.color.SkyBlue));
		gif_view.setGifImageResourceID(ID);

		addView(gif_view);

		// gif_view.setY(100);
	}

//	public void visibleEggPointer() {
//		context.runOnUiThread(new Thread() {
//			public void run() {
//				eggPointer.setVisibility(VISIBLE);
//			}
//		});
//
//	}
//
//	public void invisibleEggPointer() {
//		context.runOnUiThread(new Thread() {
//			public void run() {
//				eggPointer.setVisibility(GONE);
//			}
//		});
//
//	}
//
//	public void setEggPointerColor(final int color) {
//		context.runOnUiThread(new Thread() {
//			public void run() {
//				eggPointerColor = color;
//				// eggPointer.postInvalidate();
//				// eggPointer.setBackgroundColor(color);
//			}
//		});
//
//	}

	private void gameOverMenuButtonsListeners() {
		newGameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				context.runOnUiThread(new Thread() {
					public void run() {
						GamePlay.this.removeAllViews();
						newGamePart2();
					}
				});

			}

		});
		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.exit(0);

			}
		});
		menuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, MainActivity.class));

			}
		});
	}

	public void setDistance(int dis) {
		distance = Integer.toString(dis);
	}

	public void setCoinsCollected(int coinsCollected) {
		this.coinsCollected = Integer.toString(coinsCollected);

	}

	private void newGame() {

		enemies.removeAll(enemies);
		isGameOver = true;
		// totalMoney += Coin.money;
		int counter = getChildCount();
		for (int i = 0; i < counter; i++) {
			removeViewAt(0);
		}
		addView(boom);

		boom.setVisibility(VISIBLE);

		new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					boom.setVisibility(INVISIBLE);
					e.printStackTrace();
				}

				context.runOnUiThread(new Thread() {

					public void run() {
						GamePlay.this.removeAllViews();
						boom.setVisibility(INVISIBLE);
						init();
						// new Thread(bird).start();
						bird.start();
					}
				});

			}

		}.start();

	}

	private void newGame2() {
		enemies.removeAll(enemies);
		isGameOver = true;
		// totalMoney += Coin.money;
		int counter = getChildCount();
		for (int i = 0; i < counter; i++) {
			removeViewAt(0);
		}
		addView(boom);

		boom.setVisibility(VISIBLE);

		new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					boom.setVisibility(INVISIBLE);
					e.printStackTrace();
				}

				context.runOnUiThread(new Thread() {

					public void run() {
						context.addNewGameView();

					}
				});

			}

		}.start();

	}

	private void newGamePart1() {
		enemies.removeAll(enemies);
		isGameOver = true;
		// totalMoney += Coin.money;
		int counter = getChildCount();
		for (int i = 0; i < counter; i++) {
			removeViewAt(0);
		}
		addView(boom);

		boom.setVisibility(VISIBLE);

		new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					boom.setVisibility(INVISIBLE);
					e.printStackTrace();
				}

			}

		}.start();

	}

	private void newGamePart2() {
		context.runOnUiThread(new Thread() {

			public void run() {
				context.addNewGameView();

			}
		});
	}

	public void gameOver() {
		try {
			// Log.e("gameOver", "gameOver");
			AlertDialog.Builder alert2 = null;
			AlertDialog.Builder alert;
			alert = new AlertDialog.Builder(context);
			alert.setIcon(android.R.drawable.ic_dialog_alert);
			alert.setTitle("GameOver");
			if (bird.distance > Bird.recordDistance) {
				// Toast.makeText(context,
				// "You create a new Hi Score ..  your distance was "
				// + Bird.recordDistance + " your new is  " + Bird.distance,
				// Toast.LENGTH_LONG);

				Toast.makeText(getContext(), " Congratulations , you create a new Hi score "
						+ Integer.toString(bird.distance)
						+ " , your old one was "
						+ Integer.toString(Bird.recordDistance), Toast.LENGTH_SHORT).show();
				new Database(getContext()).updateMaxDistance();
			}

			// if (alert != null) {
			// alert.show();
			// }

			newGamePart1();
//			vibrate();
			new Database(getContext()).updateTotalCoins();
			new Thread() {
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					context.runOnUiThread(new Thread() {
						public void run() {
							GamePlay.this.removeAllViews();
							context.finish();
						}
					});
				}
			}.start();

			// ////newGame2();
			// goToMenu();
		} catch (Exception e) {
			e.printStackTrace();
			newGame2();
		}

	}

	private void vibrate() {
		context.runOnUiThread(new Thread() {
			public void run() {
				Vibrator v = (Vibrator) context
						.getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(200);
			}
		});

	}

	private void goToMenu() {
		enemies.removeAll(enemies);
		isGameOver = true;
		// totalMoney += Coin.money;
		int counter = getChildCount();
		for (int i = 0; i < counter; i++) {
			removeViewAt(0);
		}
		addView(boom);

		boom.setVisibility(VISIBLE);

		new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					boom.setVisibility(INVISIBLE);
					e.printStackTrace();
				}

				context.runOnUiThread(new Thread() {

					public void run() {
						context.startActivity(new Intent(context,
								MainActivity.class));

					}
				});

			}

		}.start();

	}

	private void init() {
//		Coin.money = 0;
		enemies = new ArrayList<Enemy>();
		Egg.eggs.removeAll(Egg.eggs);
		for (int i = 0; i < eggs.length; i++) {
			eggs[i] = new Egg(context);
		}
		// egg1 = new Egg(context);
		// egg2 = new Egg(context);
//		coinsCollected = Integer.toString(Coin.money);
		isGameOver = false;
		bird = new Bird(context);
		addView(bird);
		context.runOnUiThread(new Thread() {

			public void run() {
				invalidate();
				postInvalidate();

			}
		});

	}

	private void createTube() {
		if (context == null && isGameOver) {
			return;
		}
		Tube tube = new Tube(context);
		addView(tube);
		new Thread(tube).start();

	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/*
		 * try{ if (GeneraUpdates != null && (GeneraUpdates.equals(CLOUD_1) ||
		 * GeneraUpdates.equals(RAINING) || GeneraUpdates
		 * .equals(RAINING_NIGHT))) { Matrix matrix = canvas.getMatrix();
		 * drawBackGround(canvas); canvas.setMatrix(matrix); } }catch(Exception
		 * e){}
		 */
		Paint paint = new Paint();

		// paint.setColor(Color.BLACK);
		// canvas.drawRect(bird.getBounds(), paint)

		paint.setTextSize(getWidth() / 20);
		paint.setColor(Color.RED);
		int height = 7 * getHeight() / 8;
		if (distance != null) {
			canvas.drawText("Distance: " + distance, getWidth() / 20, height,
					paint);
			canvas.drawText("Record: " + Bird.recordDistance, getWidth() / 20,
					18 * height / 20, paint);
		}
		if (coinsCollected != null) {
			canvas.drawText("Coins: " + coinsCollected, 5 * getWidth() / 8,
					height, paint);

		}
		canvas.drawText("Wallet : " + totalMoney, 5 * getWidth() / 8,
				18 * height / 20, paint);
		paint.setColor(Color.BLACK);

	}

	public Rect getBounds() {
		return new Rect(0, 0, getWidth(), getHeight());
	}

	public String getDistance() {
		return distance;
	}

	public String getCoinsCollected() {
		return coinsCollected;
	}

	private Movie movie;
	private long moviestart;

	private void drawBackGround(Canvas canvas) {
		long now = android.os.SystemClock.uptimeMillis();
		Paint p = new Paint();
		Matrix m = new Matrix();
		float heightScale = canvas.getHeight() / (float) movie.height();
		float widthScale = canvas.getWidth() / (float) movie.width();
		m.setScale(widthScale, heightScale);
		// canvas.setMatrix(m);

		p.setAntiAlias(true);
		// float heightScale = canvas.getHeight() / (float)movie.height();
		// float widthScale = canvas.getWidth() / (float)movie.width();
		// canvas.scale(widthScale, heightScale);

		if (moviestart == 0)

			// m.setScale((int)(getWidth()/(double)movie.width()),(int)(
			// (double)getHeight()/movie.height()));
			moviestart = now;

		int relTime;
		relTime = (int) ((now - moviestart) % movie.duration());
		movie.setTime(relTime);
		canvas.setMatrix(m);

		movie.draw(canvas, 0, 0);
		invalidate();
	}

	private Thread flyThread;
	private OnTouchListener touchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (hasSuperControll) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// Log.e("in gameplat class"," action is down in touchListener");

					if (event.getY() < bird.getY() + bird.getHeight() / 2) {
						superControllDirection = "UP";
						// bird.setGoingDown(false);
					} else if (event.getY() > bird.getY() + bird.getHeight()
							/ 2) {
						// bird.setGoingDown(true);

						superControllDirection = "DOWN";
					}

				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					superControllDirection = "STREIGHT";
				}
				return true;
			} else if (hasBetterControll) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// Log.e("in gameplat class"," action is down in touchListener");
					bird.setGoingDown(false);

				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					bird.setGoingDown(true);
					// Log.e("in gameplat class",
					// " action is up in touchListener");

				}
				/*
				 * else if (GamePlay.isGameOver) {
				 * 
				 * thread.start(); if (event.getAction() ==
				 * android.view.MotionEvent.ACTION_DOWN) { if (thread == null ||
				 * !thread.isAlive()) { thread = new Thread() { public void
				 * run() { context.runOnUiThread(new Thread(){ public void
				 * run(){ newGame2(); } }); } }; // / ama exw provlima to svinw
				 * }
				 * 
				 * } }
				 */
				invalidate();
				postInvalidate();
				return true;
			} else {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// Log.e("in gameplat class"," action is down in touchListener");
					bird.setGoingDown(false);

				}

				flyThread = new Thread() {
					public void run() {
						try {

							Thread.sleep(birdSleepToFall);
							if (currentThread() == flyThread)
								bird.setGoingDown(true);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				flyThread.start();

				return false;
			}

		}

	};
	Thread thread = null;

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {

		int counter = this.getChildCount();

		for (int i = 0; i < counter; i++) {
			View v = this.getChildAt(i);

			if (v instanceof GifView) {

				v.layout(0, 0, getWidth(), getHeight());

			}
			if (v instanceof Animation) {

				v.layout(0, 0, getWidth(), getHeight());

			}
			if (v instanceof Bird) {

				v.layout((int) v.getX(), (int) v.getY(), (int) v.getX()
						+ getHeight() / 10, (int) v.getY() + getHeight() / 10);
			} else if (v instanceof Tube) {

				v.layout((int) getWidth(), 0,
						(int) getWidth() + getWidth() / 6, (int) getHeight());
			} else if (v instanceof Boom) {
				v.layout(0, 0, 3 * getWidth() / 4, 3 * getHeight() / 4);
			} else if (v instanceof Egg) {
				Egg egg = (Egg) v;
				if (!egg.isRunning()) {
					v.layout(2 * getWidth(), 0, 2 * getWidth() + getWidth()
							/ 10, +getWidth() / 13);

					new Thread(egg).start();
				}
			} else if (v == newGameButton) {
				v.layout(getWidth()/2 -getWidth()/4, getHeight() / 20,  getWidth()/2 +getWidth()/4, getHeight() / 20 + getHeight() / 4);
			} else if (v == menuButton) {
				v.layout(getWidth()/2 -getWidth()/4, getHeight() / 19 + getHeight() / 4,
						getWidth()/2 +getWidth()/4, getHeight() / 19
								+ getHeight() / 4 + getHeight() / 4);
			} else if (v == exitButton) {
				v.layout(getWidth()/2 -getWidth()/4, getHeight() / 19 + getHeight() / 4
						+ getHeight() / 4, getWidth()/2 +getWidth()/4,
						getHeight() / 19 + getHeight() / 4 + getHeight() / 4
								+ getHeight() / 4);
			}
		}

	}

	private int nearBirdY() {
		int near = 0;
		boolean isSmaller = false;
		Random rand = new Random();

		isSmaller = rand.nextBoolean();
		if (isSmaller) {
			near = (int) bird.getY() - rand.nextInt(3 * bird.getHeight());
			if (near < 0) {
				near = rand.nextInt(3 * bird.getHeight());
			}
		} else {
			near = (int) bird.getY() + rand.nextInt(3 * bird.getHeight());
			if (near > getWidth()) {
				near = (int) bird.getX() - rand.nextInt(3 * bird.getHeight());
			}
		}

		return near;
	}

	private int getRandomHeight() {
		int randomHeight = 0;
		Random rand = new Random();
		randomHeight = getHeight() / 7 + rand.nextInt(5 * getHeight() / 7);

		return randomHeight;
	}

		/*
		 * if (!coin1.isRunning()) { if (!cointainsView(coin1)) {
		 * addView(coin1); } coin1.newGame(); coin1.setBird(bird);
		 * coin1.setGamePlay(this); coin1.setY(getRandomHeight()); // new
		 * Thread(coin1).start(); } else if (!coin2.isRunning()) { if
		 * (!cointainsView(coin2)) { addView(coin2); } coin2.newGame();
		 * coin2.setBird(bird); coin2.setGamePlay(this);
		 * coin2.setY(getRandomHeight()); // new Thread(coin2).start(); } else
		 * if (!coin3.isRunning()) { if (!cointainsView(coin3)) {
		 * addView(coin3); } coin3.newGame(); coin3.setBird(bird);
		 * coin3.setGamePlay(this); coin3.setY(getRandomHeight()); // new
		 * Thread(coin2).start(); } } catch (Exception e) { e.printStackTrace();
		 * } catch (Error e) { }
		 */
//	}

	private boolean cointainsView(View v) {
		boolean contains = false;
		int counter = this.getChildCount();
		for (int i = 0; i < counter; i++) {
			if (this.getChildAt(i) == v) {
				return true;
			}
		}
		return contains;

	}

	@Override
	public void run() {

		new Thread() {
			public void run() {
				try {
					Thread.sleep(coinRation / 2);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (true) {
					try {
						if (isGameOver||GameActivity.isExited) {
							break;
						}

						Thread.sleep(coinRation);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();
		new Thread() {
			public void run() {
				// Random rand = new Random();

				while (true) {
					if (isGameOver||GameActivity.isExited) {
						break;
					}

					checkForSuccesfullMisions();
					try {
						int extra = 5635 - bird.getDistance() * 10;
						if (extra <= 0) {
							extra = 0;
						}
						eggRation = 3000 + extra;
						Thread.sleep(eggRation);
					} catch (Exception e) {
						// TODO Auto-generated catch block

						eggRation = 3000;
						try {
							Thread.sleep(eggRation);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					try {
						if (isGameOver) {
							break;
						}

						// Thread.sleep(10000);
						context.runOnUiThread(new Thread() {
							public void run() {
								for (Egg egg : eggs) {

									if (!egg.isRunning()) {
										try {
											egg.newEgg();
											egg.setY(nearBirdY());
											if (cointainsView(egg)) {
												removeView(egg);

											}

											addView(egg);

											break;
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
								/*
								 * if (!egg1.isRunning()) { try { egg1.newEgg();
								 * egg1.setY(nearBirdY()); if
								 * (!cointainsView(egg1)) { addView(egg1); }
								 * else { removeView(egg1); addView(egg1); }
								 * 
								 * } catch (Exception e) { e.printStackTrace();
								 * } } else if (!egg2.isRunning()) { try {
								 * egg2.newEgg(); egg2.setY(nearBirdY()); if
								 * (!cointainsView(egg2)) { addView(egg2); }
								 * else { removeView(egg2); addView(egg2); }
								 * 
								 * // new Thread(egg2).start(); } catch
								 * (Exception e) { e.printStackTrace(); } } else
								 * { return; }
								 */

							}
						});

					} catch (Exception e) {
						// TODO Auto-generated catch block
						newGame2();
						e.printStackTrace();
					}
				}
			}

		}.start();
		try {
			Thread.sleep(1000);// + (int) (4000 * Math.random())-
								// bird.getDistance() * 10);
		} catch (Exception e) {

		}
		context.runOnUiThread(new Thread() {
			public void run() {
				createTube();
			}
		});
		while (true) {
			try {
				if (isGameOver||GameActivity.isExited) {
					break;
				}
				int extra = 0;

				extra = Math.abs(3000 - bird.getDistance() * 10)+(int)(Math.random()*1000);

				if (extra <= 0) {
					extra = 0;

				}
				Thread.sleep(tubeRation + extra);

				if (isGameOver) {
					break;
				}
				context.runOnUiThread(new Thread() {
					public void run() {
						createTube();
					}
				});

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private void checkForSuccesfullMisions() {
		if (isReadyMissionFirstCoin()) {
			MissionActivity.addCompleteMission(context,
					MissionActivity.FIRST_COIN);
		}

		if (isReadyMissionCOLLECT_100_IN_ONE_GAME()) {
			MissionActivity.addCompleteMission(context,
					MissionActivity.COLLECT_100_Coins_IN_ONE_GAME);
		}

		if (isReadyMissionDO_100_METER()) {
			MissionActivity.addCompleteMission(context,
					MissionActivity.DO_100_METER);
		}
		if (isReadyMissionDO_300_METER()) {
			MissionActivity.addCompleteMission(context,
					MissionActivity.DO_300_METER);
		}
		if (isReadyMissionCOLLECT_50_Coins_In_One_Game()) {
			MissionActivity.addCompleteMission(context,
					MissionActivity.COLLECT_50_Coins_In_One_Game);
		}
		if (isReadyMissionDO_500_METER()) {
			MissionActivity.addCompleteMission(context,
					MissionActivity.DO_500_METER);
		}
		if (isReadyMissionCOLLECT_ALL_UPGRADES()) {
			MissionActivity.addCompleteMission(context,
					MissionActivity.COLLECT_ALL_UPGRADES);
		}
		if (isReadyMissionPASS_50_EGGS_IN_ONE_GAME()) {
			MissionActivity.addCompleteMission(context,
					MissionActivity.PASS_50_EGGS_IN_ONE_GAME);
		}
		if (isReadyMissionPASS_50_TUBES_IN_ONE_GAME()) {
			MissionActivity.addCompleteMission(context,
					MissionActivity.PASS_50_TUBES_IN_ONE_GAME);
		}
	}

	private synchronized boolean isReadyMissionFirstCoin() {
		if (MissionActivity.completeMissions
				.contains(MissionActivity.FIRST_COIN)) {
			return false;
		}
		if (totalMoney >= 1) {
			return true;
		}
		return false;
	}

	private synchronized boolean isReadyMissionCOLLECT_100_IN_ONE_GAME() {
		if (MissionActivity.completeMissions
				.contains(MissionActivity.COLLECT_100_Coins_IN_ONE_GAME)) {
			return false;
		}


		return false;
	}

	private synchronized boolean isReadyMissionDO_300_METER() {
		if (MissionActivity.completeMissions
				.contains(MissionActivity.DO_100_METER)) {
			return false;
		}

		if (Bird.recordDistance >= 300) {
			return true;
		}
		return false;
	}

	private synchronized boolean isReadyMissionDO_100_METER() {
		if (MissionActivity.completeMissions
				.contains(MissionActivity.DO_100_METER)) {
			return false;
		}

		if (Bird.recordDistance >= 100) {
			return true;
		}
		return false;
	}

	private synchronized boolean isReadyMissionCOLLECT_50_Coins_In_One_Game() {
		if (MissionActivity.completeMissions
				.contains(MissionActivity.COLLECT_50_Coins_In_One_Game)) {
			return false;
		}
		if (totalMoney >= 50) {
			return true;
		}
		return false;
	}

	private synchronized boolean isReadyMissionDO_500_METER() {
		if (MissionActivity.completeMissions
				.contains(MissionActivity.DO_500_METER)) {
			return false;
		}
		if (Bird.recordDistance >= 500) {
			return true;
		}
		return false;
	}

	private synchronized boolean isReadyMissionCOLLECT_ALL_UPGRADES() {
		if (MissionActivity.completeMissions
				.contains(MissionActivity.COLLECT_ALL_UPGRADES)) {
			return false;
		}
		if (UpgradeSelectedItemMenu.upgratedItems.size() == UpgradeSelectedItemMenu.allUpgrades
				.size()) {
			return true;
		}
		return false;
	}

	private synchronized boolean isReadyMissionPASS_50_EGGS_IN_ONE_GAME() {
		if (MissionActivity.completeMissions
				.contains(MissionActivity.PASS_50_EGGS_IN_ONE_GAME)) {
			return false;
		}
		if (Egg.counterOfPassEgg >= 50) {
			return true;
		}
		return false;
	}

	private synchronized boolean isReadyMissionPASS_50_TUBES_IN_ONE_GAME() {
		if (MissionActivity.completeMissions
				.contains(MissionActivity.PASS_50_TUBES_IN_ONE_GAME)) {
			return false;
		}
		if (Tube.counterOfPassTube >= 50) {
			return true;
		}
		return false;
	}
}
