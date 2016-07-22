package pack.tsoglani.bird.birdpack;


import android.app.Activity;
import android.graphics.Canvas;
import android.view.View;

public class Boom extends View {
	private boolean isRunning = true;
	private int maxX, maxY, minX, minY;
private Activity context;
	public Boom(Activity context) {
		super(context);
		this.context=context;
		setBackgroundResource(R.drawable.boom0);
		// TODO Auto-generated constructor stub
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

	}

	public void setminMax(int minX, int minY, int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.minX = minX;
		this.minY = minY;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
/*
	@Override
	public void run() {
		
		boolean isGrowing = true;
		while (isRunning) {
			
			if(getWidth()>maxX||getHeight()>maxY){
				isGrowing=false;
			}else if(getWidth()<minX||getHeight()<minY){
				isGrowing=true;
			}
			final boolean isGr=isGrowing;
			context.runOnUiThread(new Thread(){
				public void run(){
					if (isGr) {
						layout((int) getX() + 1, (int) getY() + 1, (int) getX()
								- 1 + getWidth(), (int) getY() - 1 + getHeight());
					}else{
						layout((int) getX() - 1, (int) getY() - 1, (int) getX()
								+ 1 + getWidth(), (int) getY() + 1 + getHeight());
					}
				}
			});
			
			
		}
		}
		*/

	

}
