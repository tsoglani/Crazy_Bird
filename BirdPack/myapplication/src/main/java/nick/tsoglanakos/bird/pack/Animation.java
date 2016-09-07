package nick.tsoglanakos.bird.pack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Paint;
import android.view.View;

import java.io.IOException;

public class Animation extends View {
	private Movie movie;
	private long moviestart;

	public Animation(Context context) {
		super(context);
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		try {
			if (GamePlay.GeneraUpdates.equals(GamePlay.CLOUD_1)) {

				movie = Movie.decodeStream(context.getResources().getAssets()
						.open("cloud.gif"));

				// addBackGround(R.drawable.cloud);
			} else if (GamePlay.GeneraUpdates.equals(GamePlay.RAINING)) {
				movie = Movie.decodeStream(context.getResources().getAssets()
						.open("rain0.gif"));
				// addBackGround(R.drawable.rain0);
				// Log.e("GeneraUpdates.equals(RAINING) = ", GeneraUpdates);
			} else if (GamePlay.GeneraUpdates.equals(GamePlay.RAINING_NIGHT)) {
				movie = Movie.decodeStream(context.getResources().getAssets()
						.open("rain3.gif"));
				// addBackGround(R.drawable.rain3);
			}else if (GamePlay.GeneraUpdates.equals(GamePlay.SNOW)) {
				movie = Movie.decodeStream(context.getResources().getAssets()
						.open("snow1.gif"));
				// addBackGround(R.drawable.rain3);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

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

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		try {
			if (GamePlay.GeneraUpdates != null
					&& (GamePlay.GeneraUpdates.equals(GamePlay.CLOUD_1)
							|| GamePlay.GeneraUpdates.equals(GamePlay.RAINING)|| GamePlay.GeneraUpdates.equals(GamePlay.SNOW) || GamePlay.GeneraUpdates
								.equals(GamePlay.RAINING_NIGHT))) {
				Matrix matrix = canvas.getMatrix();
				drawBackGround(canvas);
				canvas.setMatrix(matrix);
			}
		} catch (Exception e) {
		}
	}

}
