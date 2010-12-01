package edu.berkeley.cs160.betterbet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "betterbet.db";
    private static final int DATABASE_VERSION = 1;
	private static final String PLAYERS_TABLE_NAME = "players";
	private static final String GROUPS_TABLE_NAME = "groups";
	private static final String MEMBERSHIPS_TABLE_NAME = "memberships";
	private static final String STATS_TABLE_NAME = "statistics";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + PLAYERS_TABLE_NAME + " ("
				+ "_id INTEGER PRIMARY KEY,"
				+ "name TEXT);"
				);
		db.execSQL("CREATE TABLE " + GROUPS_TABLE_NAME + " ("
				+ "_id INTEGER PRIMARY KEY,"
				+ "name TEXT);"
				);
		db.execSQL("CREATE TABLE " + MEMBERSHIPS_TABLE_NAME + " ("
				+ "player_id INTEGER,"
				+ "group_id INTEGER,"
				+ "PRIMARY KEY (player_id, group_id) );"
				);
		db.execSQL("CREATE TABLE " + STATS_TABLE_NAME + " ("
				+ "player_id INTEGER,"
				+ "group_id INTEGER,"
				+ "wins INTEGER,"
				+ "games INTEGER,"
				+ "PRIMARY KEY (player_id, group_id) );"
				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
}
