package com.Bird.Crazy.Crazybird;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.ViewGroup;

public class TubePart extends ViewGroup {
	// private int posY, posX;
	public final static String BOTTOM = "Bottom", TOP = "Top";
	private String location;
	private int width, height;

	@SuppressWarnings("deprecation")
	public TubePart(Context context, String location) {
		super(context);
		this.location = location;
		// TODO Auto-generated constructor stub
		
		setWillNotDraw(false);
		// setBackgroundResource(R.drawable.tube1);
		// fixRotate();
		// width=((Activity)
		// context).getWindowManager().getDefaultDisplay().getWidth()/10;

		// height=((Activity)
		// context).getWindowManager().getDefaultDisplay().getHeight()/10+(int)(Math.random()*((Activity)
		// context).getWindowManager().getDefaultDisplay().getWidth()/20);
		// setX(((Activity) context).getWindowManager().getDefaultDisplay()
		// .getWidth()
		// - 100);

		// if (location.equals(BOTTOM)) {

		// setY(200);
		// for(int i=0;i<20;i++){
		// Log.e(Integer.toString(((MainActivity)
		// context).getWindowManager().getDefaultDisplay().getHeight()-200),"((MainActivity) context).getWindowManager().getDefaultDisplay().getHeight()-200");
		// }
		// }
		// else if (location.equals(TOP)) {
		// setY(0);
		// }

	}

	public int getMyWidth() {
		return width;
	}

	public int getMyHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		Bitmap bmpOriginal = BitmapFactory.decodeResource(getResources(),
				R.drawable.tube2);
		Matrix matrix = new Matrix();
		
		matrix.setScale((float) getWidth() / bmpOriginal.getWidth(),
				getHeight() / (float) bmpOriginal.getHeight());
		if (location.equals(TOP)) {
			canvas.rotate(180, getWidth() / 2, getHeight() / 2);
		}
		
		canvas.drawBitmap(bmpOriginal, matrix, paint);

	}
	
	public Rect getBounds(){
		Rect rec=new Rect();
		Tube tube=(Tube)getParent();
		if(tube==null){
			return null;
		}
		rec.set((int)tube.getX(), (int)getY(), (int)tube.getX()+(int)getWidth(), (int)getY()+(int)getHeight());
	//	if((int)getY()==0||(int)getX()==0){
		//	return null;
	//	}
		return rec;
	}

	public void setX(float x) {
		super.setX(x);

		// this.posX = posX + (int) x;
		// if (posX <= 0) {
		// posX = 0;
		// }

		// this.layout(posX, posY, posX + getWidth(), posY + getHeight());

	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		int childs = this.getChildCount();
		for (int i = 0; i < childs; i++) {
			getChildAt(i).layout(0, 0, getWidth(), getHeight());
		}

	}

}
