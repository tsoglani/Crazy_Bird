package pack.tsoglani.bird.birdpack;


import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class RoundButton extends Button {
	private Activity context;
	private boolean isPressing = false;

	public RoundButton(Activity context) {
		super(context);

		this.context = context;
		addTouchListener();
		// TODO Auto-generated constructor stub
	}

	private void addTouchListener(){
		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				final RoundButton b = (RoundButton) v;
				
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					b.setIsPressing(true);
				}else if (event.getAction() == MotionEvent.ACTION_UP){
					b.setIsPressing(false);
					
					
				}
				
				
				
				context.runOnUiThread(new Thread() {
					public void run() {
						b.invalidate();
					}
				});
				
				return false;
			}

		});
	}

	public boolean isPressing() {
		return isPressing;
	}

	public void setIsPressing(boolean isPressing) {
		this.isPressing = isPressing;

	}

	public void onDraw(Canvas canvas) {
		int prevColor = 0;

		Drawable background = getBackground();
		if (background instanceof ColorDrawable) {
			prevColor = ((ColorDrawable) background).getColor();
		}
		Paint paint = new Paint();
		if (prevColor != 0) {
			paint.setColor(prevColor);
		} else {
			paint.setColor(Color.WHITE);
		}
		canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
		if (this.isPressed() || isPressing()) {
			paint.setColor(context.getResources().getColor(R.color.Orange));
		} else {
			paint.setColor(context.getResources().getColor(R.color.OliveDrab));
		}
		RectF rectF = new RectF();
		RectF rectF2 = new RectF();
		rectF.left = getWidth() / 6;
		rectF.right = getWidth() - getWidth() / 6;
		rectF.top = getHeight() / 6;
		rectF.bottom = getHeight() - getHeight() / 6;

		int standarDistance = 5;
		rectF2.left = getWidth() / 6 + standarDistance;
		rectF2.right = getWidth() - getWidth() / 6 - standarDistance;
		rectF2.top = getHeight() / 6 + standarDistance;
		rectF2.bottom = getHeight() - getHeight() / 6 - standarDistance;
		canvas.drawOval(rectF, paint);
		if (prevColor != 0) {
			paint.setColor(prevColor);
		} else {
			paint.setColor(Color.WHITE);
		}
		canvas.drawOval(rectF2, paint);
		paint.setColor(getResources().getColor(R.color.blueback));

		paint.setTextSize(2 * (rectF2.right - rectF2.left) / (25));
		paint.setTextScaleX(getTextScaleX());

		float x = rectF2.left
				+ ((rectF2.width()) - getText().length() * paint.getTextSize()
						/ 2) / 2;
		float y = getHeight() / 2;
		canvas.drawText(getText().toString(), x, y, paint);

		// ///canvas.drawText(getText().toString(), 4 * (rectF.right -
		// rectF.left)/ getText().length() - getText().length() +
		// rectF.left,getHeight() / 2, paint);

		// if (getText().toString().contains("Background Upgrades")) {
		// canvas.drawText(getText().toString(), getWidth() / 5,
		// getHeight() / 2, paint);
		// }else{
		// canvas.drawText(getText().toString(), getWidth() / 4,
		// getHeight() / 2, paint);
		// }
	}

}
