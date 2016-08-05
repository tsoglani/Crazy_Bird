package pack.tsoglani.bird.birdpack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainActivity extends Activity {

//    private TextView mTextView;
FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//                setRequestedOrientation(GameActivity.oriantation);
                init();

                UpgradeSelectedItemMenu.addAllUpgrades();
                Database db = new Database(MainActivity.this);
                loadPaidUpgrades(db);
                loadCollectedCoins(db);
                loadMaxDistance(db);
                loadCurentGenerauUpgrade(db);
                loadCurentUsingBirdUpgrade(db);
                loadCompleteMissions(db);
                setContentView(layout);
                MenuView menuView = new MenuView(MainActivity.this);
                layout.addView(menuView);
                menuView.setBackgroundColor(getResources().getColor(R.color.transparent_white_percent_5));
                layout.setBackgroundResource(R.drawable.bird_m);

            }
        });
    }
    private void loadCurentGenerauUpgrade(Database db) {
        try {
            GamePlay.GeneraUpdates = db.getCurentGenerauUpgrade();
            if (GamePlay.GeneraUpdates == null) {
                GamePlay.GeneraUpdates = GamePlay.Clasic;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCompleteMissions(Database db) {
        try {

            ArrayList<String> array = db.getMissions();
            if (array != null)
                for (String str : array) {
                    MissionActivity.completeMissions.add(str);
                }

            Log.e("completed Missions", "success ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadCurentUsingBirdUpgrade(Database db) {
        try {
            // GamePlay.BirdUpdates = db.getCurentBirdUpgrade();
            // if (GamePlay.BirdUpdates == null) {
            // GamePlay.BirdUpdates = GamePlay.Clasic;
            // }
            ArrayList<String> array = db.getCurentBirdUpgrade();
            if (array == null) {
                return;
            }
            for (String str : array) {
                GamePlay.birdUpdatesUsing.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCollectedCoins(Database db) {
        try {
            GamePlay.totalMoney = db.getTotalCoins();
            //	GamePlay.totalMoney = 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMaxDistance(Database db) {
        try {
            Bird.recordDistance = db.getMaxDistance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPaidUpgrades(Database db) {
        try {
            ArrayList<String> array = db.getUpgrades();
            UpgradeSelectedItemMenu.upgratedItems
                    .removeAll(UpgradeSelectedItemMenu.upgratedItems);
            for (String s : array) {
                UpgradeSelectedItemMenu.upgratedItems.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FrameLayout getLayout() {
        return layout;
    }

    private void init() {
        layout = new FrameLayout(this);
        layout.setForegroundGravity(Gravity.CENTER);
    }



    /*
 *
 */
    public static void loadSound(String title, Context context) {
        AssetFileDescriptor afd;
        MediaPlayer player;
        if (title == null || title.equals("") || context == null) {

            return;
        }
        try {
            afd = context.getAssets().openFd(title + ".mp3");

            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
                    afd.getLength());

            player.prepare();
            player.start();
            afd.close();
            // player.release();
            player = null;
            afd = null;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (Exception e) {



            // e.printStackTrace();
        } catch (Error e) {

            // e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(this);
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.setTitle("closing Activity");
        alert.setMessage("Are you sure you want to close the game ?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }

        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        alert.show();

    }

}
