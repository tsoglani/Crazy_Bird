package pack.tsoglani.bird.birdpack;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

public class Tube extends ViewGroup implements Runnable, Enemy {
	private TubePart top, bottom;
	private Activity context;
	public static int counterOfPassTube = 0;

	public Tube(Activity context) {
		super(context);
		this.context = context;
		init();
		GamePlay.enemies.add(this);
		// TODO Auto-generated constructor stub
	}

	public boolean intersect(Rect rec) {
		boolean intersect = false;
		if (top.getBounds() == null || bottom.getBounds() == null) {
			return false;
		}
		if (top.getBounds().intersect(rec)) {
			intersect = true;
		}
		if (bottom.getBounds().intersect(rec)) {
			intersect = true;
		}

		return intersect;
	}

	private void init() {
		top = new TubePart(context, TubePart.TOP);
		bottom = new TubePart(context, TubePart.BOTTOM);
		addView(top);
		addView(bottom);
	}

	private int divPostition = 0;
	private int space = 0;

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		if (divPostition == 0) {
			divPostition = rand.nextInt(((Activity) context).getWindowManager()
					.getDefaultDisplay().getHeight())
					/ 15
					+ 3
					* rand.nextInt(((Activity) context).getWindowManager()
							.getDefaultDisplay().getHeight() / 5);
		}
		if (space == 0) {
			if (((Activity) context).getWindowManager().getDefaultDisplay()
					.getHeight() > ((Activity) context).getWindowManager()
					.getDefaultDisplay().getWidth()) {
				space = 10*((Activity) context).getWindowManager()
						.getDefaultDisplay().getHeight()
						  / 55;//+ rand.nextInt(((Activity) context).getWindowManager().getDefaultDisplay().getHeight() / 30)
						;
			} else {
				space = 10*((Activity) context).getWindowManager()
						.getDefaultDisplay().getHeight()
						  / 55;//+ rand.nextInt(((Activity) context).getWindowManager().getDefaultDisplay().getHeight() / 30)	;
					}
		}
	//	Log.e("((Activity) context).getWindowManager().getDefaultDisplay().getHeight() / 30", Integer.toString(((Activity) context).getWindowManager().getDefaultDisplay().getHeight() / 30));
		
		if (divPostition < 20) {
			divPostition = 20;
		}

		int childs = this.getChildCount();
		for (int i = 0; i < childs; i++) {
			View v = getChildAt(i);

			if (v instanceof TubePart && i == 0) {
				v.layout(0, i * divPostition, getWidth(), i * divPostition
						+ divPostition);

			} else {
				v.layout(0, (i) * divPostition + space, getWidth(), getHeight());

			}

		}
	}

	@Override
	public void run() {

		while (true) {
if(GameActivity.isExited){
	break;
}
			try {
				Thread.sleep(GamePlay.gameSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			context.runOnUiThread(new Thread() {
				public void run() {
					if (GamePlay.enemies.contains(Tube.this)
							&& !GamePlay.isGameOver) {
						setX(getX() - 1);
					}
				}
			});
			// if(top.getBounds()!=null)
			// Log.e("tube bound",top.getBounds().toString());
			if (GamePlay.isGameOver) {
				break;
			}
			if (getX() + getWidth() < 0) {
				// GamePlay.enemies.remove(top);
				// GamePlay.enemies.remove(bottom);
				GamePlay.enemies.remove(this);
				break;
			}

		}
		GamePlay.enemies.remove(this);
		counterOfPassTube++;
	//	Log.e("Tube finish", " pass one tube ");
	}
}
