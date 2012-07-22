package net.chenyuzhao.cafecounter;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TableRow.LayoutParams;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;
import android.view.*;
import android.widget.*;
import java.util.*;

public class TransactionLogActivity extends CCActivity {
	TableLayout tlTransactionLog;

	AccountsDataSource ads;
	TransactionsDataSource tds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_log_layout);
        
        tlTransactionLog = (TableLayout)findViewById(R.id.tlTransactionLog);
        
        tds = new TransactionsDataSource(this);
        tds.open();
        List<Transaction> transactions = tds.getAllTransactions();
        tds.close();
        
        ads = new AccountsDataSource(this);
        ads.open();
        List<Account> accounts = ads.getAllAccounts();
        ads.close();
        
        for(Transaction transaction : transactions) {
        	TableRow tr = new TableRow(this);
        	tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        	
        	TextView tvDate = new TextView(this);
        	tvDate.setText(X.timestampToDatestring(transaction.timestamp));
        	tvDate.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,1.0f));
        	
        	TextView tvAccount = new TextView(this);
        	for(Account a : accounts) {
        		if(a.id == transaction.account_id) {
        			tvAccount.setText(a.name);
        		}
        	}
        	tvAccount.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,1.0f));
        	
        	TextView tvName = new TextView(this);
        	tvName.setText(transaction.name);
        	tvName.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,2.0f));
        	
        	TextView tvDescription = new TextView(this);
        	tvDescription.setText(transaction.description);
        	tvDescription.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,3.0f));
        	
        	TextView tvAmount = new TextView(this);
        	tvAmount.setText(X.currencyToString(transaction.amount));
        	tvAmount.setGravity(Gravity.RIGHT);
        	tvAmount.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,1.0f));
        	
        	tr.addView(tvDate);
        	tr.addView(tvAccount);
        	tr.addView(tvName);
        	tr.addView(tvDescription);
        	tr.addView(tvAmount);
        	
        	tlTransactionLog.addView(tr);
        }
    }
}
