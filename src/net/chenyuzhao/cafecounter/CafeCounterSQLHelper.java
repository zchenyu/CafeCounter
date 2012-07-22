package net.chenyuzhao.cafecounter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CafeCounterSQLHelper extends SQLiteOpenHelper {
	static final String DATABASE_NAME = "cafecounter.db";
	static final int DATABASE_VERSION = 4;
	
	public CafeCounterSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db){
		Log.w("query","CREATE TABLE " + AccountsSQLHelper.TABLE_NAME + " (" +
				AccountsSQLHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				AccountsSQLHelper.COLUMN_NAME + " TEXT NOT NULL, " +
				AccountsSQLHelper.COLUMN_BALANCE + " INTEGER" +
			");");
		db.execSQL("CREATE TABLE " + AccountsSQLHelper.TABLE_NAME + " (" +
				AccountsSQLHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				AccountsSQLHelper.COLUMN_NAME + " TEXT NOT NULL, " +
				AccountsSQLHelper.COLUMN_BALANCE + " INTEGER" +
			");");
		db.execSQL("CREATE TABLE " + TransactionsSQLHelper.TABLE_NAME + " (" +
				TransactionsSQLHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				TransactionsSQLHelper.COLUMN_AMOUNT + " INTEGER, " +
				TransactionsSQLHelper.COLUMN_NAME + " TEXT, " +
				TransactionsSQLHelper.COLUMN_ADDRESS + " TEXT, " +
				TransactionsSQLHelper.COLUMN_DESCRIPTION + " TEXT, " +
				TransactionsSQLHelper.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
			");");
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(CafeCounterSQLHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + AccountsSQLHelper.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TransactionsSQLHelper.TABLE_NAME);
		onCreate(db);
	}
}
