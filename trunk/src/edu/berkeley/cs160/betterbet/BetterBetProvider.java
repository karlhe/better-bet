package edu.berkeley.cs160.betterbet;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

public class BetterBetProvider extends ContentProvider {

	public static final class PlayerColumns implements BaseColumns {
		private PlayerColumns() {}
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/players");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.betterbet.player";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.betterbet.player";
		public static final String DEFAULT_SORT_ORDER = "name DESC";
		public static final String NAME = "name";
		public static final String PLAYER = "player";
	}
	
	private DatabaseHelper dbHelper;
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	
	public static String AUTHORITY = "edu.berkeley.cs160.betterbet.DatabaseProvider";
	public static final Uri PLAYERS_URI = Uri.parse("content://" + AUTHORITY + "/users");
	private static HashMap<String, String> sPlayersProjectionMap;
	private static final int GET_PLAYERS = 0;
	private static final int GET_PLAYER = 1;
	
	private static UriMatcher buildUriMatcher() {
		UriMatcher matcher =  new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(AUTHORITY, "players", GET_PLAYERS);
		matcher.addURI(AUTHORITY, "players/#", GET_PLAYER);
		return matcher;
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case GET_PLAYERS:
			return PlayerColumns.CONTENT_TYPE;
		case GET_PLAYER:
			return PlayerColumns.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		ContentValues values;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		switch (sUriMatcher.match(uri)) {
		case GET_PLAYERS:
			if (initialValues != null) {
				values = new ContentValues(initialValues);
			} else {
				throw new SQLException("Failed insert due to missing values.");
			}
			long rowId = db.insert("players", PlayerColumns.PLAYER, values);
			if (rowId > 0) {
				Uri playerUri = ContentUris.withAppendedId(PlayerColumns.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(playerUri, null);
				return playerUri;
			}
			throw new SQLException("Failed to insert row into " + uri);
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String orderBy;
		
		switch (sUriMatcher.match(uri)) {
		case GET_PLAYERS:
			qb.setTables("players");
			qb.setProjectionMap(sPlayersProjectionMap);
			orderBy = PlayerColumns.DEFAULT_SORT_ORDER;
			break;
		case GET_PLAYER:
			qb.setTables("players");
			qb.setProjectionMap(sPlayersProjectionMap);
			qb.appendWhere(PlayerColumns._ID + "=" + uri.getPathSegments().get(1));
			orderBy = PlayerColumns.DEFAULT_SORT_ORDER;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		// Get the database and run the query
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		
		// Tell the cursor what uri to watch, so it knows when its source data changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}
	
	static {
		sPlayersProjectionMap = new HashMap<String, String>();
		sPlayersProjectionMap.put(PlayerColumns._ID, PlayerColumns._ID);
		sPlayersProjectionMap.put(PlayerColumns.NAME, PlayerColumns.NAME);
	}

}
