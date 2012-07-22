package net.chenyuzhao.cafecounter;

import java.util.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.Log;

public class AccountsDataSource {
	private SQLiteDatabase db;
	private AccountsSQLHelper dbHelper;
	private final String[] allColumns = {
			AccountsSQLHelper.COLUMN_ID,
			AccountsSQLHelper.COLUMN_NAME,
			AccountsSQLHelper.COLUMN_BALANCE
		};
	
	public AccountsDataSource(Context context) {
		dbHelper = new AccountsSQLHelper(context);
	}
	
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public void insertAccount(Account account) {
		ContentValues values = new ContentValues();
		values.put(AccountsSQLHelper.COLUMN_NAME, account.name);
		values.put(AccountsSQLHelper.COLUMN_BALANCE, account.balance);
		db.insert(AccountsSQLHelper.TABLE_NAME, null, values);
	}
	
	public void deleteAccount(long id) {
		db.delete(AccountsSQLHelper.TABLE_NAME, AccountsSQLHelper.COLUMN_ID + "=" + id, null);
	}
	
	public void updateAccount(Account account) {
		ContentValues values = new ContentValues();
		values.put(AccountsSQLHelper.COLUMN_NAME, account.name);
		values.put(AccountsSQLHelper.COLUMN_BALANCE, account.balance);
		db.update(AccountsSQLHelper.TABLE_NAME, values, AccountsSQLHelper.COLUMN_ID + "=" + account.id, null);
	}
	
	public Account getAccount(long id) {
		Cursor cursor = db.query(AccountsSQLHelper.TABLE_NAME, allColumns, AccountsSQLHelper.COLUMN_ID + "=" + id, null, null, null, null);
		cursor.moveToFirst();
		return cursorToAccount(cursor);
	}
	
	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		Cursor cursor = db.query(AccountsSQLHelper.TABLE_NAME, allColumns, null, null, null, null, AccountsSQLHelper.COLUMN_ID);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Account account = cursorToAccount(cursor);
			accounts.add(account);
			cursor.moveToNext();
		}
		cursor.close();
		return accounts;
	}
	
	private Account cursorToAccount(Cursor cursor) {
		long id = cursor.getLong(0);
		String name = cursor.getString(1);
		long balance = cursor.getLong(2);
		return new Account(id, name, balance);
	}
}
