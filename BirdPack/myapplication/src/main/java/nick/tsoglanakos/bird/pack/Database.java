package nick.tsoglanakos.bird.pack;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	private static boolean hasUpdate = false;
	// Database Name
	private static final String DATABASE_NAME = "My_DataBase";

	// Contacts table name
	private static final String TABLE_CONTACTS = "ITEMS_DB",
			TABLE_MISSION = "MISSION_DB", Bird_UPDATE_DB = "BirdArray";
	private static final String TABLE_COINS_CONTACTS = "CoinTable";
	private static final String TABLE_DISTANCE_CONTACTS = "DISTANCE_DB";
	private static final String TABLE_GeneraUpdates_CONTACTS = "CURENT_GENeral_UPDATE_DB";
	private static final String TABLE_BirdUpdates_CONTACTS = "CURENT_BIRD_UPGRADE_DB";
	// Contacts Table Columns names
	private static final String KEY_ID = "idf";
	private static final String GeneraUpdates_ID = "CurentGeneralUpgrade_id",
			Bird_ID = "bird_id";
	private static final String BirdUpdates_ID = "CurentBirdUpgrade_id",
			Mission_ID = "Mission_TB";
	private static final String COINS_ID = "coins_id";
	private static final String ITEM_ID = "DB_UPD";
	private static final String DISTANCE_ID = "MaxDistance_id";
	private static ArrayList<Integer> listId = new ArrayList<Integer>();

	/**
	 * 
	 * @param context
	 */
	private static boolean oneTime = false;

	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		oneTime=false;
		// removeAll2() ;
		// reset();
		onCreate(getWritableDatabase());
		// onCreate(getWritableDatabase());

	}

	public void reset() {
		if (oneTime) {
			return;
		}
	
		oneTime = true;
		destroyTable(TABLE_CONTACTS);
		destroyTable(TABLE_MISSION);
		destroyTable(TABLE_COINS_CONTACTS);
		destroyTable(TABLE_DISTANCE_CONTACTS);
		destroyTable(TABLE_GeneraUpdates_CONTACTS);
		destroyTable(TABLE_BirdUpdates_CONTACTS);
		destroyTable(Bird_UPDATE_DB);
		
		GamePlay.birdUpdatesUsing.removeAll(GamePlay.birdUpdatesUsing);
		GamePlay.birdUpdatesUsing.add(GamePlay.Clasic);
		GamePlay.GeneraUpdates=GamePlay.Clasic;
		UpgradeSelectedItemMenu.upgratedItems.removeAll(UpgradeSelectedItemMenu.allUpgrades);
		UpgradeSelectedItemMenu.upgratedItems.add(GamePlay.Clasic);
		GamePlay.GeneraUpdates=GamePlay.Clasic;
		updateCurentBirdUpgrade();
		updateCurentGeneraUpgrade();
	}

	/**
 * 
 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		try {
			String Upgrates_Table = "CREATE TABLE " + TABLE_CONTACTS + "("
					+ KEY_ID + " INTEGER PRIMARY KEY," + ITEM_ID + " TEXT"
					+ ")";

			/*
			 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
			 * "(" + KEY_ID + " INTEGER PRIMARY KEY," + BUTTON_ID +
			 * " INTEGER,"+HINT_ID + " TEXT" + ")";
			 */

			// db.execSQL(CREATE_CONTACTS_TABLE);
			db.execSQL(Upgrates_Table);

			// db.close();

		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {

			String Score_Table = "CREATE TABLE " + TABLE_COINS_CONTACTS + "("
					+ KEY_ID + " INTEGER PRIMARY KEY," + COINS_ID + " INTEGER"
					+ ")";
			/*
			 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
			 * "(" + KEY_ID + " INTEGER PRIMARY KEY," + BUTTON_ID +
			 * " INTEGER,"+HINT_ID + " TEXT" + ")";
			 */

			// db.execSQL(CREATE_CONTACTS_TABLE);

			db.execSQL(Score_Table);
			// db.close();

		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {

			String Score_Table = "CREATE TABLE " + TABLE_DISTANCE_CONTACTS
					+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + DISTANCE_ID
					+ " INTEGER" + ")";
			/*
			 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
			 * "(" + KEY_ID + " INTEGER PRIMARY KEY," + BUTTON_ID +
			 * " INTEGER,"+HINT_ID + " TEXT" + ")";
			 */

			// db.execSQL(CREATE_CONTACTS_TABLE);

			db.execSQL(Score_Table);
			// db.close();

		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {
			String Score_Table = "CREATE TABLE " + TABLE_GeneraUpdates_CONTACTS
					+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + GeneraUpdates_ID
					+ " TEXT" + ")";
			db.execSQL(Score_Table);
			// db.delete(TABLE_COINS_CONTACTS, KEY_ID + " = 0", null);
		} catch (Exception e) {
		}
		try {
			String Score_Table = "CREATE TABLE " + TABLE_BirdUpdates_CONTACTS
					+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + BirdUpdates_ID
					+ " TEXT" + ")";
			db.execSQL(Score_Table);
			// db.delete(TABLE_COINS_CONTACTS, KEY_ID + " = 0", null);
		} catch (Exception e) {
		}

		try {

			String Cr_Ta = "CREATE TABLE " + TABLE_MISSION + "(" + KEY_ID
					+ " INTEGER PRIMARY KEY," + Mission_ID + " TEXT" + ")";
			/*
			 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
			 * "(" + KEY_ID + " INTEGER PRIMARY KEY," + BUTTON_ID +
			 * " INTEGER,"+HINT_ID + " TEXT" + ")";
			 */
			// db.execSQL(CREATE_CONTACTS_TABLE);
			db.execSQL(Cr_Ta);
		} catch (Exception e) {

		}

		try {

			String Cr_Ta = "CREATE TABLE " + Bird_UPDATE_DB + "(" + KEY_ID
					+ " INTEGER PRIMARY KEY," + Bird_ID + " TEXT" + ")";
			/*
			 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
			 * "(" + KEY_ID + " INTEGER PRIMARY KEY," + BUTTON_ID +
			 * " INTEGER,"+HINT_ID + " TEXT" + ")";
			 */
			// db.execSQL(CREATE_CONTACTS_TABLE);
			db.execSQL(Cr_Ta);
		} catch (Exception e) {
		
		}
		
	}

	public void updateCurentGeneraUpgrade() {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_GeneraUpdates_CONTACTS);
			// db.delete(TABLE_COINS_CONTACTS, KEY_ID + " = 0", null);
		} catch (Exception e) {
		}
		try {
			String Score_Table = "CREATE TABLE " + TABLE_GeneraUpdates_CONTACTS
					+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + GeneraUpdates_ID
					+ " TEXT" + ")";
			db.execSQL(Score_Table);
			// db.delete(TABLE_COINS_CONTACTS, KEY_ID + " = 0", null);
		} catch (Exception e) {
		}
		ContentValues values = new ContentValues();
		values.put(KEY_ID, 0);
		values.put(GeneraUpdates_ID, GamePlay.GeneraUpdates);
		// Log.e("Bird.recordDistance = ",Integer.toString(Bird.recordDistance));
		db.insert(TABLE_GeneraUpdates_CONTACTS, null, values);

		db.close();
	}

	public String getCurentGenerauUpgrade() {
		String curGenUpgrade = null;
		String selectQuery = "SELECT  * FROM " + TABLE_GeneraUpdates_CONTACTS;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// UpgradeSelectedItemMenu.upgratedItems
		// .removeAll(UpgradeSelectedItemMenu.upgratedItems);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				// int currentFoundButtonId = 0;

				// id = Integer.parseInt(cursor.getString(0));
				curGenUpgrade = cursor.getString(1);
				Log.e("cursor.getInt(1) = ", Integer.toString(cursor.getInt(1)));
				// int hint= cursor.getInt(2);
				// contact.setName(cursor.getString(1));
				// contact.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				// array.add(currentFoundButtonId);
				// listId.add(id);
			} while (cursor.moveToNext());

		}
		return curGenUpgrade;
	}

	/*
	 * public void updateCurentBirdUpgrade() { SQLiteDatabase db =
	 * this.getWritableDatabase(); try { //db.execSQL("DROP TABLE IF EXISTS " +
	 * TABLE_GeneraUpdates_CONTACTS); db.delete(TABLE_BirdUpdates_CONTACTS,
	 * KEY_ID + " = 0", null); } catch (Exception e) { } try { String
	 * Score_Table = "CREATE TABLE " + TABLE_BirdUpdates_CONTACTS + "(" + KEY_ID
	 * + " INTEGER PRIMARY KEY," + BirdUpdates_ID + " TEXT" + ")";
	 * db.execSQL(Score_Table); // db.delete(TABLE_COINS_CONTACTS, KEY_ID +
	 * " = 0", null); } catch (Exception e) { } ContentValues values = new
	 * ContentValues(); values.put(KEY_ID, 0); values.put(BirdUpdates_ID,
	 * GamePlay.BirdUpdates); //
	 * Log.e("Bird.recordDistance = ",Integer.toString(Bird.recordDistance));
	 * db.insert(TABLE_BirdUpdates_CONTACTS, null, values);
	 * 
	 * db.close(); }
	 */
	/*
	 * public String getCurentBirdUpgrade() { String curGenUpgrade = null;
	 * String selectQuery = "SELECT  * FROM " + TABLE_BirdUpdates_CONTACTS;
	 * SQLiteDatabase db = getWritableDatabase(); Cursor cursor =
	 * db.rawQuery(selectQuery, null); // UpgradeSelectedItemMenu.upgratedItems
	 * // .removeAll(UpgradeSelectedItemMenu.upgratedItems); // looping through
	 * all rows and adding to list if (cursor.moveToFirst()) { do { // int
	 * currentFoundButtonId = 0;
	 * 
	 * // id = Integer.parseInt(cursor.getString(0)); curGenUpgrade =
	 * cursor.getString(1); Log.e("cursor.getInt(1) = ",
	 * Integer.toString(cursor.getInt(1))); // int hint= cursor.getInt(2); //
	 * contact.setName(cursor.getString(1)); //
	 * contact.setPhoneNumber(cursor.getString(2)); // Adding contact to list //
	 * array.add(currentFoundButtonId); // listId.add(id); } while
	 * (cursor.moveToNext());
	 * 
	 * } Log.e("TABLE_BirdUpdates_CONTACTS = ",curGenUpgrade); return
	 * curGenUpgrade; }
	 */
	public void updateMaxDistance() {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTANCE_CONTACTS);
			// db.delete(TABLE_COINS_CONTACTS, KEY_ID + " = 0", null);
		} catch (Exception e) {
		}
		try {
			String Score_Table = "CREATE TABLE " + TABLE_DISTANCE_CONTACTS
					+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + DISTANCE_ID
					+ " INTEGER" + ")";
			db.execSQL(Score_Table);
			// db.delete(TABLE_COINS_CONTACTS, KEY_ID + " = 0", null);
		} catch (Exception e) {
		}
		ContentValues values = new ContentValues();
		values.put(KEY_ID, 0);
		values.put(DISTANCE_ID, Bird.recordDistance);
		// Log.e("Bird.recordDistance = ",Integer.toString(Bird.recordDistance));
		db.insert(TABLE_DISTANCE_CONTACTS, null, values);

		db.close();
	}

	public int getMaxDistance() {
		int maxDistance = 0;
		String selectQuery = "SELECT  * FROM " + TABLE_DISTANCE_CONTACTS;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// UpgradeSelectedItemMenu.upgratedItems
		// .removeAll(UpgradeSelectedItemMenu.upgratedItems);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				// int currentFoundButtonId = 0;

				// id = Integer.parseInt(cursor.getString(0));
				maxDistance = cursor.getInt(1);
				Log.e("cursor.getInt(1) = ", Integer.toString(cursor.getInt(1)));
				// int hint= cursor.getInt(2);
				// contact.setName(cursor.getString(1));
				// contact.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				// array.add(currentFoundButtonId);
				// listId.add(id);
			} while (cursor.moveToNext());
		}
		Log.e("Integer.toString(maxDistance) = ", Integer.toString(maxDistance));
		return maxDistance;
	}

	public void updateTotalCoins() {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_COINS_CONTACTS);
			// db.delete(TABLE_COINS_CONTACTS, KEY_ID + " = 0", null);
		} catch (Exception e) {
		}
		try {
			String Score_Table = "CREATE TABLE " + TABLE_COINS_CONTACTS + "("
					+ KEY_ID + " INTEGER PRIMARY KEY," + COINS_ID + " INTEGER"
					+ ")";
			db.execSQL(Score_Table);
			// db.delete(TABLE_COINS_CONTACTS, KEY_ID + " = 0", null);
		} catch (Exception e) {
		}
		ContentValues values = new ContentValues();
		values.put(KEY_ID, 0);
		values.put(COINS_ID, GamePlay.totalMoney); // Contact
													// button
													// id
		// values.put(HINT_ID, Integer.toString(hint)); // hints
		// Number

		// Inserting Row
		db.insert(TABLE_COINS_CONTACTS, null, values);

		db.close();
	}

	public int getTotalCoins() {
		int bestScore = 0;
		String selectQuery = "SELECT  * FROM " + TABLE_COINS_CONTACTS;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// UpgradeSelectedItemMenu.upgratedItems
		// .removeAll(UpgradeSelectedItemMenu.upgratedItems);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				// int currentFoundButtonId = 0;

				// id = Integer.parseInt(cursor.getString(0));
				bestScore = cursor.getInt(1);

				// int hint= cursor.getInt(2);
				// contact.setName(cursor.getString(1));
				// contact.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				// array.add(currentFoundButtonId);
				// listId.add(id);
			} while (cursor.moveToNext());
		}

		return bestScore;
	}

	/**
 * 
 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			Log.e("Database ", "on Upgrade ");
		}
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * 
	 * @param contact
	 */
	/*
	 * public void addContact(int button_id, int hint) { try { if
	 * (this.getAllContacts().contains(button_id)) { return; }
	 * 
	 * SQLiteDatabase db = this.getWritableDatabase();
	 * 
	 * ContentValues values = new ContentValues(); values.put(BUTTON_ID,
	 * button_id); // Contact button id values.put(HINT_ID,
	 * Integer.toString(hint)); // hints // Number
	 * 
	 * // Inserting Row db.insert(TABLE_CONTACTS, null, values); db.close(); //
	 * Closing database connection } catch (Exception e) { try {
	 * addContact(button_id, hint); } catch (Exception ex) {
	 * 
	 * } } }
	 */

	private void destroyTable(String table) {
		try {
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL("DROP TABLE IF EXISTS " + table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createUpgradesTable() {
		try {
			SQLiteDatabase db = getWritableDatabase();
			String Cr_Ta = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID
					+ " INTEGER PRIMARY KEY," + ITEM_ID + " TEXT" + ")";
			/*
			 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
			 * "(" + KEY_ID + " INTEGER PRIMARY KEY," + BUTTON_ID +
			 * " INTEGER,"+HINT_ID + " TEXT" + ")";
			 */
			// db.execSQL(CREATE_CONTACTS_TABLE);
			db.execSQL(Cr_Ta);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateUpgrates(ArrayList<String> items) {
		try {
			// if (this.getAllContacts().contains(button_id)) {
			// return;
			// }
			SQLiteDatabase db = getWritableDatabase();

			destroyTable(TABLE_CONTACTS);
			createUpgradesTable();

			/*
			 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
			 * "(" + KEY_ID + " INTEGER PRIMARY KEY," + BUTTON_ID +
			 * " INTEGER,"+HINT_ID + " TEXT" + ")";
			 */
			// db.execSQL(CREATE_CONTACTS_TABLE);

			ContentValues values = new ContentValues();
			// byte [] bites=getItems(items);
			for (int i = 0; i < UpgradeSelectedItemMenu.upgratedItems.size(); i++) {
				values.put(KEY_ID, i);
				values.put(ITEM_ID,
						UpgradeSelectedItemMenu.upgratedItems.get(i)); // Contact
																		// button
																		// id
				// values.put(HINT_ID, Integer.toString(hint)); // hints
				// Number

				// Inserting Row
				db.insert(TABLE_CONTACTS, null, values);
			}
			db.close(); // Closing database connection
			Log.e("Sucess", "updateUpgrates");
		} catch (Exception e) {
			try {
				updateUpgrates(items);
			} catch (Exception ex) {

			}
		}

	}

	/*
	 * private byte[] getItems(ArrayList<String> list) { ByteArrayOutputStream
	 * baos = new ByteArrayOutputStream(); DataOutputStream out = new
	 * DataOutputStream(baos); for (String element : list) { try {
	 * out.writeUTF(element); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } } byte[] bytes = baos.toByteArray();
	 * 
	 * // read from byte array ByteArrayInputStream bais = new
	 * ByteArrayInputStream(bytes); DataInputStream in = new
	 * DataInputStream(bais); try { while (in.available() > 0) { String element
	 * = in.readUTF(); Log.e(element, element); } } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } return bytes; }
	 */

	public ArrayList<String> getUpgrades() throws NullPointerException {
		ArrayList<String> array = null;
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		array = new ArrayList<String>();
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// UpgradeSelectedItemMenu.upgratedItems
		// .removeAll(UpgradeSelectedItemMenu.upgratedItems);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				// int currentFoundButtonId = 0;
				String upgrade = null;
				// id = Integer.parseInt(cursor.getString(0));
				upgrade = cursor.getString(1);

				array.add(upgrade);

				// int hint= cursor.getInt(2);
				// contact.setName(cursor.getString(1));
				// contact.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				// array.add(currentFoundButtonId);
				// listId.add(id);
			} while (cursor.moveToNext());
		}

		return array;
	}

	/**
	 * 
	 * @return
	 */
	// Getting All Contacts
	/*
	 * public ArrayList<Integer> getAllContacts() { ArrayList<Integer>
	 * contactList = new ArrayList<Integer>(); // Select All Query String
	 * selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
	 * 
	 * SQLiteDatabase db = getWritableDatabase(); Cursor cursor =
	 * db.rawQuery(selectQuery, null);
	 * 
	 * // looping through all rows and adding to list if (cursor.moveToFirst())
	 * { do { int currentFoundButtonId = 0; int id = 0; id =
	 * Integer.parseInt(cursor.getString(0)); currentFoundButtonId =
	 * cursor.getInt(1);
	 * 
	 * // int hint= cursor.getInt(2); // contact.setName(cursor.getString(1));
	 * // contact.setPhoneNumber(cursor.getString(2)); // Adding contact to list
	 * contactList.add(currentFoundButtonId); listId.add(id); } while
	 * (cursor.moveToNext()); } db.close(); // return contact list return
	 * contactList; }
	 */
	public void updateHints() {
		if (hasUpdate) {
			return;
		}
		hasUpdate = true;
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		String hint = null;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				hint = cursor.getString(2);
			} while (cursor.moveToNext());
		}
		// if(hint!=null)
		// EnigmaAct.hints=Integer.parseInt(hint);

	}

	public void updateCompleteMissions() {
		try {
			// if (this.getAllContacts().contains(button_id)) {
			// return;
			// }
			SQLiteDatabase db = getWritableDatabase();

			destroyTable(TABLE_MISSION);
			try {

				String Cr_Ta = "CREATE TABLE " + TABLE_MISSION + "(" + KEY_ID
						+ " INTEGER PRIMARY KEY," + Mission_ID + " TEXT" + ")";
				/*
				 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
				 * TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
				 * BUTTON_ID + " INTEGER,"+HINT_ID + " TEXT" + ")";
				 */
				// db.execSQL(CREATE_CONTACTS_TABLE);
				db.execSQL(Cr_Ta);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
			 * "(" + KEY_ID + " INTEGER PRIMARY KEY," + BUTTON_ID +
			 * " INTEGER,"+HINT_ID + " TEXT" + ")";
			 */
			// db.execSQL(CREATE_CONTACTS_TABLE);

			ContentValues values = new ContentValues();
			// byte [] bites=getItems(items);
			for (int i = 0; i < MissionActivity.completeMissions.size(); i++) {
				values.put(KEY_ID, i);
				values.put(Mission_ID, MissionActivity.completeMissions.get(i)); // Contact
																					// button
																					// id
				// values.put(HINT_ID, Integer.toString(hint)); // hints
				// Number

				// Inserting Row
				db.insert(TABLE_MISSION, null, values);
			}
			db.close(); // Closing database connection
			Log.e("Sucess", "Mission update ");
		} catch (Exception e) {

		}
	}

	public ArrayList<String> getMissions() throws NullPointerException {
		ArrayList<String> array = null;
		String selectQuery = "SELECT  * FROM " + TABLE_MISSION;
		array = new ArrayList<String>();
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// UpgradeSelectedItemMenu.upgratedItems
		// .removeAll(UpgradeSelectedItemMenu.upgratedItems);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				// int currentFoundButtonId = 0;
				String upgrade = null;
				// id = Integer.parseInt(cursor.getString(0));
				upgrade = cursor.getString(1);

				array.add(upgrade);

				// int hint= cursor.getInt(2);
				// contact.setName(cursor.getString(1));
				// contact.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				// array.add(currentFoundButtonId);
				// listId.add(id);
			} while (cursor.moveToNext());
		}

		return array;
	}

	public void updateCurentBirdUpgrade() {
		try {
			// if (this.getAllContacts().contains(button_id)) {
			// return;
			// }
			SQLiteDatabase db = getWritableDatabase();

			destroyTable(Bird_UPDATE_DB);
			try {

				String Cr_Ta = "CREATE TABLE " + Bird_UPDATE_DB + "(" + KEY_ID
						+ " INTEGER PRIMARY KEY," + Bird_ID + " TEXT" + ")";
				/*
				 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
				 * TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
				 * BUTTON_ID + " INTEGER,"+HINT_ID + " TEXT" + ")";
				 */
				// db.execSQL(CREATE_CONTACTS_TABLE);
				db.execSQL(Cr_Ta);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
			 * "(" + KEY_ID + " INTEGER PRIMARY KEY," + BUTTON_ID +
			 * " INTEGER,"+HINT_ID + " TEXT" + ")";
			 */
			// db.execSQL(CREATE_CONTACTS_TABLE);

			ContentValues values = new ContentValues();
			// byte [] bites=getItems(items);
			for (int i = 0; i < GamePlay.birdUpdatesUsing.size(); i++) {
				values.put(KEY_ID, i);
				values.put(Bird_ID, GamePlay.birdUpdatesUsing.get(i)); // Contact
																				// button
																				// id
				// values.put(HINT_ID, Integer.toString(hint)); // hints
				// Number

				// Inserting Row
				db.insert(Bird_UPDATE_DB, null, values);
			}
			db.close(); // Closing database connection
			Log.e("Sucess", "Bird_UPDATE_DB update ");
		} catch (Exception e) {

		}
	}

	public ArrayList<String> getCurentBirdUpgrade() throws NullPointerException {
		ArrayList<String> array = null;
		String selectQuery = "SELECT  * FROM " + Bird_UPDATE_DB;
		array = new ArrayList<String>();
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// UpgradeSelectedItemMenu.upgratedItems
		// .removeAll(UpgradeSelectedItemMenu.upgratedItems);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				// int currentFoundButtonId = 0;
				String upgrade = null;
				// id = Integer.parseInt(cursor.getString(0));
				upgrade = cursor.getString(1);

				array.add(upgrade);

				// int hint= cursor.getInt(2);
				// contact.setName(cursor.getString(1));
				// contact.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				// array.add(currentFoundButtonId);
				// listId.add(id);
			} while (cursor.moveToNext());
		}

		return array;
	}
	
	
	/**
		  * 
		  */
	// Deleting all contacts
	/*
	 * public void removeAll() { if (listId.isEmpty()) { getAllContacts(); } if
	 * (listId.isEmpty()) { return; } SQLiteDatabase db =
	 * this.getWritableDatabase(); ArrayList<Integer> array = getAllContacts();
	 * int counter = getCorrectCount(); while (!listId.isEmpty()) {
	 * Log.e("removeAll", "function"); Log.e("number ",
	 * Integer.toString(counter)); db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
	 * new String[] { String.valueOf(listId.remove(0)) }); } db.close();
	 * 
	 * }
	 */
	public void removeAll2() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// EnigmaAct.hints=EnigmaAct.startingHints;
		// Create tables again
		onCreate(db);
	}

	/*
	 * public void removeAll(){ SQLiteDatabase db = this.getWritableDatabase();
	 * db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS); db.close(); }
	 */

	// public int getCorrectCount() {
	// return getAllContacts().size();
	// }

}
