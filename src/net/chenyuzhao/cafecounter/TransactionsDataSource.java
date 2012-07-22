package net.chenyuzhao.cafecounter;

import java.util.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.Log;

public class TransactionsDataSource {
	private SQLiteDatabase db;
	private TransactionsSQLHelper dbHelper;
	private final String[] allColumns = {
			TransactionsSQLHelper.COLUMN_ID,
			TransactionsSQLHelper.COLUMN_AMOUNT,
			TransactionsSQLHelper.COLUMN_NAME,
			TransactionsSQLHelper.COLUMN_ADDRESS,
			TransactionsSQLHelper.COLUMN_DESCRIPTION,
			TransactionsSQLHelper.COLUMN_TIMESTAMP,
			TransactionsSQLHelper.COLUMN_ACCOUNT_ID,
		};
	
	public TransactionsDataSource(Context context) {
		dbHelper = new TransactionsSQLHelper(context);
	}
	
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public void createTransaction(String amount, String name, String address, String description, String timestamp, String account_id) {
		ContentValues values = new ContentValues();
		values.put(TransactionsSQLHelper.COLUMN_AMOUNT, amount);
		values.put(TransactionsSQLHelper.COLUMN_NAME, name);
		values.put(TransactionsSQLHelper.COLUMN_ADDRESS, address);
		values.put(TransactionsSQLHelper.COLUMN_DESCRIPTION, description);
		values.put(TransactionsSQLHelper.COLUMN_TIMESTAMP, timestamp);
		values.put(TransactionsSQLHelper.COLUMN_ACCOUNT_ID, account_id);
		db.insert(TransactionsSQLHelper.TABLE_NAME, null, values);
	}
	
	public void deleteTransaction(Transaction transaction) {
		long id = transaction.id;
		db.delete(TransactionsSQLHelper.TABLE_NAME, TransactionsSQLHelper.COLUMN_ID + "=" + id, null);
	}
	
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		Cursor cursor = db.query(TransactionsSQLHelper.TABLE_NAME, allColumns, null, null, null, null, TransactionsSQLHelper.COLUMN_ID + " DESC");
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Transaction transaction = cursorToTransaction(cursor);
			transactions.add(transaction);
			cursor.moveToNext();
		}
		cursor.close();
		return transactions;
	}
	
	private Transaction cursorToTransaction(Cursor cursor) {
		long id = cursor.getLong(0);
		long amount = cursor.getLong(1);
		String name = cursor.getString(2);
		String address = cursor.getString(3);
		String description = cursor.getString(4);
		long timestamp = cursor.getLong(5);
		long account_id = cursor.getLong(6);
		return new Transaction(id, amount, name, address, description, timestamp, account_id);
	}
}
