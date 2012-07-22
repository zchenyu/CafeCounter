package net.chenyuzhao.cafecounter;

import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;

public class TransactionsSQLHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "cafecounter_transactions.db";
	private static final int DATABASE_VERSION = 7;
	public static final String TABLE_NAME = "transactions";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_TIMESTAMP = "timestamp";
	public static final String COLUMN_ACCOUNT_ID = "account_id";
	
	public TransactionsSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TransactionsSQLHelper.TABLE_NAME + " (" +
				TransactionsSQLHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				TransactionsSQLHelper.COLUMN_AMOUNT + " INTEGER, " +
				TransactionsSQLHelper.COLUMN_NAME + " TEXT, " +
				TransactionsSQLHelper.COLUMN_ADDRESS + " TEXT, " +
				TransactionsSQLHelper.COLUMN_DESCRIPTION + " TEXT, " +
				TransactionsSQLHelper.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
				TransactionsSQLHelper.COLUMN_ACCOUNT_ID + " INTEGER" +
			");");
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(CafeCounterSQLHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TransactionsSQLHelper.TABLE_NAME);
		onCreate(db);
	}
}
