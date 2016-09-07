package nick.tsoglanakos.bird.pack;

import java.io.IOException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Paint;
import android.view.View;

public class AnimationToView {
private View view;
private Movie movie;
private String imageTitle;
private Context context;
private long moviestart;
	public AnimationToView(Context context,View view) {
		this.view=view;
		this.context=context;
	}
	public void setImage(String title){
		imageTitle=title;
		try {
			movie = Movie.decodeStream(context.getResources().getAssets()
					.open("cloud.gif"));
			moviestart=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void drawBackGround(Canvas canvas) {
		if(movie==null ){
			return;
		}
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
		view.invalidate();
	}


}
