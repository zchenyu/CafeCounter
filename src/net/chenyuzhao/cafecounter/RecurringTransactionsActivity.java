package net.chenyuzhao.cafecounter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class RecurringTransactionsActivity extends Activity {

	ListView lvRecurringTransactions;
	Button btnNewRecurringTransactions;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recurring_transactions_layout);
        
        lvRecurringTransactions = (ListView)findViewById(R.id.lvRecurringTransactions);
        btnNewRecurringTransactions = (Button)findViewById(R.id.btnNewRecurringTransaction);
        
        btnNewRecurringTransactions.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(RecurringTransactionsActivity.this, EditRecurringTransactionActivity.class);
				startActivity(intent);
			}
		});
    }
}
