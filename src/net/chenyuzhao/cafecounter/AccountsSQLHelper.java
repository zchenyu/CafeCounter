package net.chenyuzhao.cafecounter;

import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;

public class AccountsSQLHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "cafecounter_accounts.db";
	private static final int DATABASE_VERSION = 5;
	public static final String TABLE_NAME = "accounts";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_BALANCE = "balance";
	
	
	public AccountsSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE " + AccountsSQLHelper.TABLE_NAME + " (" +
				AccountsSQLHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				AccountsSQLHelper.COLUMN_NAME + " TEXT NOT NULL, " +
				AccountsSQLHelper.COLUMN_BALANCE + " INTEGER" +
			");");
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(CafeCounterSQLHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + AccountsSQLHelper.TABLE_NAME);
		onCreate(db);
	}
}
