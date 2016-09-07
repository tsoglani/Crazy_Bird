package nick.tsoglanakos.bird.pack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoActivity extends Activity {
	private LinearLayout layout;
	private TextView txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();
		setContentView(layout);
		generateText();
		addComponent();
	}

	private void generateText() {

		txt.setText(" The purpose of this game is to control the bird and try to avoid the tubes and the eggs that come on to you, at the same time you have to complete game's Achievements, also you can collect coins, and with them, unlock  game's upgrades .    \n");
		txt.append("\n\nThanks for playing \n I hope to enjoy it\n\n");
		txt.append("The Game Created By \nNikos Gaitanis ");
	}

	private void init() {
		layout = new LinearLayout(this);
		txt = new TextView(this);
	}

	private void addComponent() {
		layout.addView(txt);
	}

	public void onBackPressed() {
		startActivity(new Intent(this, MenuActivity.class));
	}
}
