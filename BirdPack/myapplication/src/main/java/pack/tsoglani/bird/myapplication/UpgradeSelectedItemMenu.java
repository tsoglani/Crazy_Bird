package pack.tsoglani.bird.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

//import android.widget.Button;

public class UpgradeSelectedItemMenu extends Activity {
	private RelativeLayout layout;
	private ScrollView scroll;
	private LinearLayout sl;
	private String curentSelectedUpgradeCategory;
	private final String General = "Backgrounds", BIRD = "Bird";
	// private int layoutId;
	public static ArrayList<String> upgratedItems = new ArrayList<String>() {
		public boolean add(String s) {
			if (!upgratedItems.contains(s)) {
				return super.add(s);
			} else {
				return false;
			}
		}
	};
	public static ArrayList<String> allUpgrades = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		create();
		layout.setBackgroundColor(this.getResources().getColor(R.color.Gainsboro));
	}

	private void create() {
		if (GamePlay.birdUpdatesUsing.isEmpty()) {
			GamePlay.birdUpdatesUsing.add(GamePlay.Clasic);
		}
		setRequestedOrientation(GameActivity.oriantation);
		upgratedItems.add(GamePlay.Clasic);
		layout = new RelativeLayout(this);
		setContentView(layout);
		init();
		// View inflatedView = View.inflate(this, layout.hashCode(), null);
		addScrollView();
		addRemainingCoinsView();
		// addView(inflatedView);
		scroll.addView(sl);
		generateUpgrades();
		addUpgrades();
	}

	private void init() {
		// layout =(RelativeLayout) this.findViewById(R.id.upgrade_id);// new
		// RelativeLayout(this);
		sl = new LinearLayout(this);
		scroll = new ScrollView(this);

		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// GameMenuView view= new GameMenuView(this);

		sl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		sl.setOrientation(LinearLayout.VERTICAL);

	}

	@Override
	public void setContentView(int layoutResID) {
		// this.layoutId = layoutResID;
		super.setContentView(layoutResID);
	}

	private void addScrollView() {
		// smallerWindow();

		int height = (int) (8.0 * getWindowManager().getDefaultDisplay()
				.getHeight() / 10);

		// Changes the height and width to the specified *pixels*

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				getWindowManager().getDefaultDisplay().getWidth(), height);
		layout.addView(scroll, params);
		scroll.setOnTouchListener(new OnSwipeTouchListener(getApplication()));

		layout.setOnTouchListener(new OnSwipeTouchListener(getApplication()));
		// scroll.setBackgroundColor(Color.RED);
	}

	/*
	 * private void smallerWindow() { LayoutParams par =
	 * layout.getLayoutParams(); int heightMinus =
	 * getWindowManager().getDefaultDisplay().getHeight() / 5, widthMinus =
	 * getWindowManager() .getDefaultDisplay().getWidth() / 5; ; par.height =
	 * getWindowManager().getDefaultDisplay().getHeight() - heightMinus;
	 * par.width = getWindowManager().getDefaultDisplay().getWidth() -
	 * widthMinus; layout.setX(widthMinus / 2); layout.setY(heightMinus / 6);
	 * layout.setLayoutParams(par); }
	 */
	private void addRemainingCoinsView() {

		TextView txt = new TextView(this);
		txt.setBackgroundColor(getResources().getColor(R.color.GreenYellow));
		if (UpgradeActivity.curectSelectedUpgradeToWatch
				.equals(UpgradeActivity.updateGeneral)) {
			txt.setText("Collected Coins :  "
					+ Integer.toString(GamePlay.totalMoney) + "\n"
					+ "you can activate only one per game");
		} else {
			txt.setText("Collected Coins :  "
					+ Integer.toString(GamePlay.totalMoney));
		}
		txt.setGravity(Gravity.CENTER);
		int height = (int) (getWindowManager().getDefaultDisplay().getHeight() / 10.0);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				getWindowManager().getDefaultDisplay().getWidth(), height);
		params.leftMargin = 0;
		params.topMargin = getWindowManager().getDefaultDisplay().getHeight()
				- 2 * height - height / 3;
		// txt.setLayoutParams(params);
	
		layout.addView(txt, params);
	}

	private void generateUpgrades() {
		UpgradeButtonArray.removeAll(UpgradeButtonArray);
		allUpgrades.removeAll(allUpgrades);
		if (UpgradeActivity.curectSelectedUpgradeToWatch
				.equals(UpgradeActivity.updateGeneral)) {
			curentSelectedUpgradeCategory = General;
			addView(GamePlay.Clasic, "cg1.png", 0);
			addView(GamePlay.COIN, "coin1.png", 10);
			addView(GamePlay.RAINING, "rain0.gif", 20);
			addView(GamePlay.RAINING_NIGHT, "rain3.gif", 20);
			addView(GamePlay.SNOW, "snow1.gif", 20);
			addView(GamePlay.CLOUD_1, "cloud.gif", 20);

		} else if (UpgradeActivity.curectSelectedUpgradeToWatch
				.equals(UpgradeActivity.upgradeBird)) {
			curentSelectedUpgradeCategory = BIRD;
			addView(GamePlay.Clasic, "cg1.png", 0);
			addView(GamePlay.Faster, "faster1.png", 50);
			addView(GamePlay.DelayEnemies, "slow0.png", 50);
			addView(GamePlay.SMALLER_JUMP, "jump.png", 100);
			addView(GamePlay.BETTER_CONTROL, "controller2.png", 100);
			addView(GamePlay.Master_CONTROL, "joypad4.png", 300);
		}

		showRemainingCoins();

	}

	public static void addAllUpgrades() {
		allUpgrades.add(GamePlay.Clasic);
		allUpgrades.add(GamePlay.RAINING);
		allUpgrades.add(GamePlay.RAINING_NIGHT);
		allUpgrades.add(GamePlay.SNOW);
		allUpgrades.add(GamePlay.COIN);
		allUpgrades.add(GamePlay.CLOUD_1);
		allUpgrades.add(GamePlay.Clasic);
		allUpgrades.add(GamePlay.Faster);
		allUpgrades.add(GamePlay.DelayEnemies);
		allUpgrades.add(GamePlay.BETTER_CONTROL);
		allUpgrades.add(GamePlay.SMALLER_JUMP);
	}

	private void showRemainingCoins() {
		this.runOnUiThread(new Thread() {
			public void run() {
				Toast.makeText(
						UpgradeSelectedItemMenu.this,
						"coins remaining : "
								+ Integer.toString(GamePlay.totalMoney),
						Toast.LENGTH_LONG).show();
			}
		});

	}

	private void addView(View view) {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				getWindowManager().getDefaultDisplay().getWidth(),
				getWindowManager().getDefaultDisplay().getHeight() / 5);
		layoutParams.setMargins(0, getWindowManager().getDefaultDisplay()
				.getHeight() / 80, 0, getWindowManager().getDefaultDisplay()
				.getHeight() / 80);

		// FrameLayout.LayoutParams par =new
		// FrameLayout.LayoutParams(layout.getLayoutParams().width,layout.getLayoutParams().height);
		// int heightMinus = getWindowManager().getDefaultDisplay().getHeight()
		// / 5, widthMinus = getWindowManager()
		// .getDefaultDisplay().getWidth() / 5;

		// par.height = getWindowManager().getDefaultDisplay().getHeight() -
		// heightMinus;
		// par.width = getWindowManager().getDefaultDisplay().getWidth()-
		// widthMinus;

		// sl.setLayoutParams(par);
		// LinearLayout.LayoutParams newlayoutParams = new
		// LinearLayout.LayoutParams(
		// par.width, par.height / 4);

		//Log.e("addView", "addView");
		sl.addView(view, layoutParams);// , newlayoutParams);
	}

	private boolean contains(View v) {

		for (int i = 0; i < sl.getChildCount(); i++) {
			if (sl.getChildAt(i) == v) {
				return true;
			}
		}
		return false;
	}

	/*
	 * private void addView(String title) { Button b = new Button(this);
	 * addView(b, title);
	 * 
	 * }
	 */
	/*
	 * private void addView(Button b, String title) {
	 * 
	 * b.setTextSize(this.getWindowManager().getDefaultDisplay().getWidth() /
	 * 10); b.setTextColor(Color.RED); b.setText(title);
	 * 
	 * sl.addView(b, new LinearLayout.LayoutParams(getWindowManager()
	 * .getDefaultDisplay().getWidth(), getWindowManager()
	 * .getDefaultDisplay().getHeight() / 6));
	 * 
	 * }
	 */
	private ArrayList<UpgradeButton> UpgradeButtonArray = new ArrayList<UpgradeButton>();

	private void addView(final String title, final String imageName, int coins) {

		UpgradeButton b = new UpgradeButton(this, imageName, title, coins);
		allUpgrades.add(title);
		/*
		 * b.setWillNotDraw(false);
		 * b.setTextSize(this.getWindowManager().getDefaultDisplay().getWidth()
		 * / 10); b.setTextColor(Color.RED); b.setText(title);
		 * 
		 * sl.addView(b, new LinearLayout.LayoutParams(getWindowManager()
		 * .getDefaultDisplay().getWidth(), getWindowManager()
		 * .getDefaultDisplay().getHeight() / 6));
		 */
		UpgradeButtonArray.add(b);
		// addView(b);

	}

	private void addUpgrades() {

		int size = UpgradeButtonArray.size();
		ArrayList<View> UVGS = new ArrayList<View>();
		UpgrateViewGroup uvg = new UpgrateViewGroup(
				UpgradeSelectedItemMenu.this);
		for (int i = 0; i < size; i++) {
			// addView(UpgradeButtonArray.get(i));
			// uvg.addView(UpgradeButtonArray.get(i));

			uvg.addView(UpgradeButtonArray.get(i));

			if ((i + 1) % 2 == 0) {
				UVGS.add(uvg);
				uvg = new UpgrateViewGroup(UpgradeSelectedItemMenu.this);
			}
		}
		if (!UVGS.contains(uvg)) {
			UVGS.add(uvg);
		}

		for (View v : UVGS) {
	
			addView(v);
		}

	}

	private class UpgradeButton extends ViewGroup {
		private String imageName;
		private String title;
		private int coins;
		private TextView imageV;
		private TextView text;

		public UpgradeButton(Context context, String imageName, String title,
				int coins) {
			super(context);
			this.imageName = imageName;
			this.title = title;
			this.coins = coins;
			imageV = new TextView(context);
			imageV.setGravity(Gravity.TOP);
			addTextViews(context);
			addButtonAndListeners(context);

		}

		private void addTextViews(Context context) {
			text = new TextView(context) {
				public void onDraw(Canvas canvas) {
					super.onDraw(canvas);

					// Matrix previousMatrix = canvas.getMatrix();
					Paint paint = new Paint();

					try {
						Drawable d = null;
						if (upgratedItems.contains(title)) {
							d = Drawable.createFromStream(
									getAssets().open("Correct.png"), null);
						} else {
							d = Drawable.createFromStream(
									getAssets().open("Incorrect.png"), null);
						}
						Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

						float scaleX = getWidth() / (float) bitmap.getWidth()
								/ 6, scaleY = getHeight()
								/ (float) bitmap.getHeight() / 4;

						// canvas.scale(scaleX, scaleY);
						Matrix matrix = new Matrix();

						matrix.setScale(scaleX, scaleY);
						canvas.translate((getWidth() - getWidth() / 5),
								getHeight() / 4);
						canvas.drawBitmap(bitmap, matrix, paint);
						// canvas.translate(-(getWidth() - getWidth() / 5),
						// -getHeight() / 4);
						canvas.translate(-(getWidth() - getWidth() / 5),
								-getHeight() / 4);
						float textHeightPos = getHeight() / 3;
						float textWidthPos = getWidth() / 20;

						d = Drawable.createFromStream(
								getAssets().open("coin1.png"), null);
						Bitmap bitmap2 = ((BitmapDrawable) d).getBitmap();
						scaleY = getHeight() / (float) bitmap2.getHeight() / 2;
						scaleX = scaleY;// getWidth() / (float)
										// bitmap2.getWidth() / 5;

						matrix = new Matrix();

						matrix.setScale(scaleX, scaleY);
						canvas.translate(textWidthPos, textHeightPos);
						canvas.drawBitmap(bitmap2, matrix, paint);
						canvas.translate(-textWidthPos, -textHeightPos);
						textWidthPos += scaleX * bitmap2.getWidth();
						textHeightPos = getHeight() - getHeight() / 5;

						paint.setTextSize(getHeight() - getHeight() / 3);
						canvas.drawText(Integer.toString(coins), textWidthPos,
								textHeightPos, paint);
						

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				private void addFoundItImage(Canvas canvas, Paint paint) {
					Drawable d = null;
					try {
						if (upgratedItems.contains(title)) {
							d = Drawable.createFromStream(
									getAssets().open("Correct.png"), null);
						} else {
							d = Drawable.createFromStream(
									getAssets().open("Incorrect.png"), null);
						}
						Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

						float scaleX = getWidth() / (float) bitmap.getWidth()
								/ 6, scaleY = getHeight()
								/ (float) bitmap.getHeight() / 4;

						// canvas.scale(scaleX, scaleY);
						Matrix matrix = new Matrix();

						matrix.setScale(scaleX, scaleY);
						canvas.translate((getWidth() - getWidth() / 5),
								getHeight() / 4);
						canvas.drawBitmap(bitmap, matrix, paint);
					} catch (Exception e) {
					}
				}

			};
			String in = title;
			if (in.contains(".")) {
				int end = in.indexOf(".");
				int start = 0;
				in.substring(start, end);
			}

			// text.setTextSize(10);

			if (title.equals(GamePlay.Master_CONTROL)) {
				// text.setText(in + "\n(" + GamePlay.Faster + " required)\n");
				// + "coins  : " + Integer.toString(coins));

				imageV.setText(in);
				imageV.append("\n(" + GamePlay.Faster + " required)\n");
			} else if (title.equals(GamePlay.Clasic)) {
				// text.setText(in + "\n(" + GamePlay.Faster + " required)\n");
				// + "coins  : " + Integer.toString(coins));
				// imageV.setGravity(Gravity.BOTTOM);
				//imageV.setTextScaleX(2);
				//imageV.setText(in);
			} else {
				imageV.setText(in);
				// text.setText(in + " \n" + "coins  : " +
				// Integer.toString(coins));
			}
			addView(text);
			text.setBackgroundColor(Color.GRAY);

		}

		private void addButtonAndListeners(Context context) {

			setWillNotDraw(false);
			addView(imageV);
			int textSize = UpgradeSelectedItemMenu.this.getWindowManager()
					.getDefaultDisplay().getWidth() / 4 / 8;
			imageV.setTextSize(textSize);
			if (upgratedItems.contains(title)) {

				text.setBackgroundColor(Color.RED);
				imageV.setTextColor(Color.RED);
				
				if (GamePlay.birdUpdatesUsing.contains(title)
						&& UpgradeActivity.curectSelectedUpgradeToWatch
								.equals(UpgradeActivity.upgradeBird)) {

					text.setBackgroundColor(Color.GREEN);

				} else if (GamePlay.GeneraUpdates.equals(title)
						&& UpgradeActivity.curectSelectedUpgradeToWatch
								.equals(UpgradeActivity.updateGeneral)) {

					text.setBackgroundColor(Color.GREEN);

				} else {

					text.setBackgroundColor(context.getResources().getColor(
							R.color.DarkGreen));

				}

				imageV.invalidate();
				invalidate();

			} else {

			//	imageV.setTextScaleX(1.3f);
				// text.append("\n\n LOCKED");

				invalidate();
			}
			if (imageName != null) {
				Drawable d;
				try {
					d = Drawable.createFromStream(getAssets().open(imageName),
							null);
					// Bitmap myLogo = ((BitmapDrawable) d).getBitmap();
					imageV.setBackground(d);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			setOnClickListener(listener);
			imageV.setOnClickListener(listener);
		}

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ViewGroup vg = (ViewGroup) v;
				/*
				 * Button b=null; for(int i =0 ;i<vg.getChildCount();i++){
				 * if(vg.getChildAt(i)instanceof Button){
				 * b=(Button)vg.getChildAt(i); } }
				 */
				if (!upgratedItems.contains(title)) {
					if (GamePlay.totalMoney < coins) {
						Toast.makeText(UpgradeSelectedItemMenu.this,
								"your coins are not enough", Toast.LENGTH_SHORT)
								.show();
						return;
					}

					GamePlay.totalMoney -= coins;
					upgratedItems.add(title);

					Database db = new Database(getContext());
					db.updateUpgrates(upgratedItems);
					db.updateTotalCoins();

				}
				if (curentSelectedUpgradeCategory == General) {
					GamePlay.GeneraUpdates = title;
					try {
						new Database(getContext()).updateCurentGeneraUpgrade();
					} catch (Exception e) {
					}
					// startActivity(new
					// Intent(UpgradeSelectedItemMenu.this,MenuActivity.class));

				}
				if (curentSelectedUpgradeCategory == BIRD) {
					// GamePlay.BirdUpdates = title; ////////
					// Log.e("adding ", "adding");
					if (title.equals(GamePlay.Master_CONTROL)
							&& !upgratedItems.contains(GamePlay.Faster)) {
						Toast.makeText(
								UpgradeSelectedItemMenu.this,
								"your have to buy " + GamePlay.Faster
										+ " first", Toast.LENGTH_SHORT).show();
						return;
					}
					if (title.equals(GamePlay.BETTER_CONTROL)
							&& !upgratedItems.contains(GamePlay.Master_CONTROL)) {
						Toast.makeText(
								UpgradeSelectedItemMenu.this,
								"your have to disable "
										+ GamePlay.Master_CONTROL + " first ",
								Toast.LENGTH_SHORT).show();
						return;
					}

					// Log.e("adding0 ", "adding0");
					if (title.equals(GamePlay.Clasic)) {
						GamePlay.birdUpdatesUsing
								.removeAll(GamePlay.birdUpdatesUsing);
						GamePlay.birdUpdatesUsing.add(GamePlay.Clasic);
					} else {
						// if
						// (GamePlay.birdUpdatesUsing.contains(GamePlay.Clasic))
						// {

						// }
						if (GamePlay.birdUpdatesUsing.contains(title)) {

							if (title.equals(GamePlay.Faster)
									&& GamePlay.birdUpdatesUsing
											.contains(GamePlay.Master_CONTROL)) {
								Toast.makeText(
										UpgradeSelectedItemMenu.this,
										"your have to disable "
												+ GamePlay.Master_CONTROL
												+ " first ", Toast.LENGTH_SHORT)
										.show();
								return;
							}
							GamePlay.birdUpdatesUsing.remove(title);
						} else {
							if (GamePlay.birdUpdatesUsing
									.contains(GamePlay.Clasic)) {
								GamePlay.birdUpdatesUsing
										.remove(GamePlay.Clasic);
							}

							if (title.equals(GamePlay.Master_CONTROL)) {
								// Log.e("adding1", "adding1");
								if (!GamePlay.birdUpdatesUsing
										.contains(GamePlay.Faster)) {
									GamePlay.birdUpdatesUsing
											.add(GamePlay.Faster);
								}
								if (GamePlay.birdUpdatesUsing
										.contains(GamePlay.BETTER_CONTROL)) {
									GamePlay.birdUpdatesUsing
											.remove(GamePlay.BETTER_CONTROL);
								}
								if (GamePlay.birdUpdatesUsing
										.contains(GamePlay.SMALLER_JUMP)) {
									GamePlay.birdUpdatesUsing
											.remove(GamePlay.SMALLER_JUMP);
								}
							}
							if (title.equals(GamePlay.BETTER_CONTROL)) {
								if (GamePlay.birdUpdatesUsing
										.contains(GamePlay.SMALLER_JUMP)) {
									GamePlay.birdUpdatesUsing
											.remove(GamePlay.SMALLER_JUMP);
								}

							}
							if (title.equals(GamePlay.SMALLER_JUMP)) {

								if (GamePlay.birdUpdatesUsing
										.contains(GamePlay.BETTER_CONTROL)) {
									GamePlay.birdUpdatesUsing
											.remove(GamePlay.BETTER_CONTROL);
								}
								if (GamePlay.birdUpdatesUsing
										.contains(GamePlay.Master_CONTROL)) {
									GamePlay.birdUpdatesUsing
											.remove(GamePlay.Master_CONTROL);
								}
							}

							// Log.e("adding2", "adding2");
							GamePlay.birdUpdatesUsing.add(title);

						}
					}
					if (GamePlay.birdUpdatesUsing.isEmpty()) {
						GamePlay.birdUpdatesUsing.add(GamePlay.Clasic);
					}
					try {
						new Database(getContext()).updateCurentBirdUpgrade();
					} catch (Exception e) {
					}
					// startActivity(new Intent(UpgradeSelectedItemMenu.this,
					// MenuActivity.class));

				}

				UpgradeSelectedItemMenu.this.runOnUiThread(new Thread() {
					public void run() {
						layout.removeAllViews();
						create();
					}
				});

				postInvalidate();
			}

		};

		public void onDraw(Canvas c) {
			super.onDraw(c);
			/*
			 * try { Paint paint = new Paint(); if (imageName != null) {
			 * 
			 * // Drawable d;
			 * 
			 * Drawable d = Drawable.createFromStream(
			 * getAssets().open(imageName), null); Bitmap myLogo =
			 * ((BitmapDrawable) d).getBitmap(); Matrix m = c.getMatrix();
			 * Matrix oldM = c.getMatrix(); m.setScale(getWidth() / (float)
			 * myLogo.getWidth() / 11 * 6, getHeight() / (float)
			 * myLogo.getHeight() * 10 / 11); c.setMatrix(m);
			 * c.drawBitmap(myLogo, (float) getWidth() / 30, (float) getHeight()
			 * * 6, paint);// ///////////////////// c.setMatrix(oldM); }
			 * paint.setTextSize(getHeight() / 5); paint.setColor(Color.BLUE);
			 * if (title != null) { c.drawText("Name: " + title, 3 * getWidth()
			 * / 5, getHeight() / 4, paint); } paint.setTextSize(getHeight() /
			 * 5); c.drawText("Coins : " + Integer.toString(coins), 3 *
			 * getWidth() / 5, getHeight() - getHeight() / 6, paint); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
		}

		@Override
		protected void onLayout(boolean arg0, int arg1, int arg2, int arg3,
				int arg4) {
			int sum = this.getChildCount();
			for (int i = 0; i < sum; i++) {
				View v = this.getChildAt(i);
				if (v == imageV) {
					TextView b = (TextView) v;
					b.layout(0, 0, getWidth(), getHeight() - 3 * getHeight()
							/ 9);
				} else if (v instanceof TextView) {
					TextView tv = (TextView) v;
					tv.layout(0, getHeight() - 3 * getHeight() / 9, getWidth(),
							getHeight());
				}
			}
		}
	}

	public void onBackPressed() {
		startActivity(new Intent(this, UpgradeActivity.class));
	}

	private class UpgrateViewGroup extends ViewGroup {

		public UpgrateViewGroup(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onLayout(boolean arg0, int arg1, int arg2, int arg3,
				int arg4) {
			int childCount = this.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = this.getChildAt(i);
				child.layout(i * getWidth() / 2, 0, i * getWidth() / 2 + 10
						* getWidth() / 21, getHeight() - getHeight() / 6);
			}

		}

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
//
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
