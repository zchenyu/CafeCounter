package net.chenyuzhao.cafecounter;

import android.os.*;
import android.app.*;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class MainActivity extends CCActivity {
	Button btnManageAccounts, btnNewTransaction, btnAnalytics, btnTransactionLog, btnRecurringTransactions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.main_layout);
        
        btnManageAccounts = (Button)findViewById(R.id.btnManageAccounts);
        btnNewTransaction = (Button)findViewById(R.id.btnNewTransaction);
        btnAnalytics = (Button)findViewById(R.id.btnAnalytics);
        btnTransactionLog = (Button)findViewById(R.id.btnTransactionLog);
        btnRecurringTransactions = (Button)findViewById(R.id.btnRecurringTransactions);
        
        btnManageAccounts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ManageAccountsActivity.class);
				startActivity(intent);
			}
		});
		
        btnNewTransaction.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, EditTransactionActivity.class);
				startActivity(intent);
			}
		});
		
        btnAnalytics.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AnalyticsActivity.class);
				startActivity(intent);
			}
		});
        
        btnTransactionLog.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TransactionLogActivity.class);
				startActivity(intent);
			}
		});
        
        btnRecurringTransactions.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RecurringTransactionsActivity.class);
				startActivity(intent);
			}
		});
    }
}

