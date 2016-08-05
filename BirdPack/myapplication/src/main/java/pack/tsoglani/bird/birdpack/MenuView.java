package pack.tsoglani.bird.birdpack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MenuView extends ViewGroup {
	private MainActivity context;
	private boolean isPlaying = false;

	public MenuView(MainActivity context) {
		super(context);
		this.context = context;
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		RelativeLayout sv = new RelativeLayout(context);// to ScrollView mpenei panw sto
												// layout tou activity
		sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// GameMenuView view= new GameMenuView(this);
		LinearLayout ll = new LinearLayout(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, context.getWindowManager().getDefaultDisplay()
				.getHeight() / 50, 0, context.getWindowManager()
				.getDefaultDisplay().getHeight() / 50);
		ll.setLayoutParams(lp);
		ll.setOrientation(LinearLayout.VERTICAL);

		sv.addView(ll);

		generateButtons(ll);
//		addResetButton(ll);
		ll.setGravity(Gravity.CENTER);
		sv.setGravity(Gravity.CENTER);
		context.getLayout().addView(sv);
	}

//	private void addResetButton(LinearLayout layout) {
//		MyVGroup vg = new MyVGroup(context);
//
//		layout.addView(vg, new LinearLayout.LayoutParams(context
//				.getWindowManager().getDefaultDisplay().getWidth(), context
//				.getWindowManager().getDefaultDisplay().getHeight() / 8));
//	}

	private void generateButtons(LinearLayout layout) {
		addMenuButtons(layout, "New Game");
//		addMenuButtons(layout, "Upgrades");
		addMenuButtons(layout, "Achievements");
//		 addMenuButtons( layout, "High Scores");
		addMenuButtons(layout, "Info");
//		addMenuButtons(layout, "Exit");
	}

	private void addMenuButtons(LinearLayout layout, String text) {
		Button button = new Button (context);

		button.setText(text);
		// button.setBackgroundColor(context.getResources().getColor(color.transparent_white_percent_20));
		button.setOnClickListener(buttonListener);

		layout.addView(button, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	OnClickListener buttonListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Button b = (Button) arg0;
			if (b.getText().equals("New Game")) {
				if (isPlaying) {
					return;
				}
				isPlaying = true;
				context.startActivity(new Intent(context, GameActivity.class));
			} else if (b.getText().equals("Exit")) {
				AlertDialog.Builder alert;
				alert = new AlertDialog.Builder(context);
				alert.setIcon(android.R.drawable.ic_dialog_alert);
				alert.setTitle("closing Activity");
				alert.setMessage("Are you sure you want to close the game ?");

				alert.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.exit(0);
							}

						});

				alert.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}

						});

				alert.show();
			} else if (b.getText().equals("Upgrades")) {
				context.startActivity(new Intent(context, UpgradeActivity.class));
			} else if (b.getText().equals("Achievements")) {
				Log.e("selected","Achievements");

				context.startActivity(new Intent(context, MissionActivity.class));
			} else if (b.getText().equals("Info")) {
				Log.e("selected","Info");
				context.startActivity(new Intent(context, InfoActivity.class));
			}
		}

	};

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCound = getChildCount();
		for (int i = 0; i < childCound; i++) {
			getChildAt(i).layout(0, 0, getWidth(), getHeight());
		}

	}

//	private class MyVGroup extends ViewGroup {
//		private Button button;
//
//		public MyVGroup(Context context) {
//			super(context);
//			init();
//			addView(button);
//
//			addButtonListener();
//			// TODO Auto-generated constructor stub
//		}
//
//		private void init() {
//			button = new RoundButton(context);
//			button.setGravity(Gravity.CENTER);
//			button.setText("Reset Game");
//		}
//
//		private void addButtonListener() {
//			button.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					AlertDialog.Builder alert;
//					alert = new AlertDialog.Builder(context);
//					alert.setIcon(android.R.drawable.ic_dialog_alert);
//					alert.setTitle("Reset Game");
//					alert.setMessage("Are you sure you want to reset tis game ?");
//
//					alert.setPositiveButton("Yes",
//							new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									Database d = new Database(context);
//									d.reset();
//								}
//
//							});
//
//					alert.setNegativeButton("No",
//							new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//
//								}
//
//							});
//
//					alert.show();
//
//				}
//			});
//		}
//
//		@Override
//		protected void onLayout(boolean changed, int l, int t, int r, int b) {
//			int countChild = getChildCount();
//			for (int i = 0; i < countChild; i++) {
//				View v = this.getChildAt(i);
//				v.layout(getWidth() / 2, getHeight()/3, getWidth(), getHeight());
//			}
//
//		}
//	}

}
