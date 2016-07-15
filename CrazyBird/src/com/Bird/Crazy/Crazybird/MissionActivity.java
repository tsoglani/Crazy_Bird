package com.Bird.Crazy.Crazybird;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MissionActivity extends Activity {
	private RelativeLayout layout;
	private LinearLayout scrollLayout;
	private ScrollView scroll;
	public static ArrayList<String> allMissions = new ArrayList<String>();
	public static ArrayList<String> completeMissions = new ArrayList<String>() {
		public boolean add(String s) {
			if (!completeMissions.contains(s)) {
				return super.add(s);
			}
			return false;
		}
	};
	public static ArrayList<TextView> textViewMisions = new ArrayList<TextView>();
	public static final String COLLECT_100_Coins_IN_ONE_GAME = "Collect 100 Coins in one Game",
			COLLECT_50_Coins_In_One_Game = "Collect 50 Coins in one Game",
			COLLECT_ALL_UPGRADES = "Collect all upgrades",
			DO_500_METER = "Run 500 m in one game",
			FIRST_COIN = "Collect your first coin",
			PASS_50_TUBES_IN_ONE_GAME = "Pass 50 tubes in one game ",
			PASS_50_EGGS_IN_ONE_GAME = "Pass 50 eggs in one game",
			DO_100_METER = "Run 100 m in one game",
			DO_300_METER = "Run 300 m in one game";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(GameActivity.oriantation);
		init();
		setContentView(layout);
		addScroll();
		generateMissions();
		addMissions();

	}

	private void generateMissions() {
		addMisiion(FIRST_COIN);
		addMisiion(DO_100_METER);
		addMisiion(PASS_50_TUBES_IN_ONE_GAME);
		addMisiion(PASS_50_EGGS_IN_ONE_GAME);
		addMisiion(DO_300_METER);
		addMisiion(COLLECT_50_Coins_In_One_Game);
		addMisiion(COLLECT_ALL_UPGRADES);
		addMisiion(COLLECT_100_Coins_IN_ONE_GAME);
		addMisiion(DO_500_METER);
	}

	public static void addCompleteMission(final Activity context,
			final String str) {
		if (!completeMissions.contains(str)) {
			Log.e("new Achievement complete = ", str);
			completeMissions.add(str);
			context.runOnUiThread(new Thread() {
				public void run() {
					Toast.makeText(context, "you complete mission : " + str,
							Toast.LENGTH_LONG).show();
				}
			});
			new Database(context).updateCompleteMissions();
		}
	}

	public static void addCompleteMissions(ArrayList<String> array) {

		completeMissions.removeAll(completeMissions);
		for (String str : array) {
			completeMissions.add(str);
		}
	}

	private void init() {
		layout = new RelativeLayout(this);
		scrollLayout = new LinearLayout(this);
		scroll = new ScrollView(this);
	}

	private void addScroll() {
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// GameMenuView view= new GameMenuView(this);

		scrollLayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		scrollLayout.setOrientation(1);
		layout.addView(scroll);
		scroll.addView(scrollLayout);
	}

	private void addMissions() {

		for (String str : allMissions) {
			TextView text = new TextView(this);
			text.setText(str);
			if (!completeMissions.contains(str)) {
				text.setBackgroundColor(this.getResources().getColor(
						R.color.Red));
			} else {
				text.setBackgroundColor(this.getResources().getColor(
						R.color.Green));
			}
			text.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					getWindowManager().getDefaultDisplay().getWidth(),
					getWindowManager().getDefaultDisplay().getHeight() / 10);
			layoutParams.setMargins(0, getWindowManager().getDefaultDisplay()
					.getHeight() / 80, 0, getWindowManager()
					.getDefaultDisplay().getHeight() / 80);
			scrollLayout.addView(text, layoutParams);
			textViewMisions.add(text);
		}
	}

	public void addMisiion(String s) {
		if (!allMissions.contains(s)) {
			allMissions.add(s);
		}
	}

	public void onBackPressed() {
		startActivity(new Intent(this, MenuActivity.class));
	}
}
