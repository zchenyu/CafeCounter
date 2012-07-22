package net.chenyuzhao.cafecounter;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.widget.*;

public class EditRecurringTransactionActivity extends CCActivity {
	AccountsDataSource ads;
	TransactionsDataSource tds;
	
	Spinner spnRPAccounts;
	RadioButton btnRPMakePayment, btnRPReceivePayment;
	Spinner spnRPFrequency;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recurring_transaction_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);

		spnRPAccounts = (Spinner)findViewById(R.id.spnRPAccounts);
		btnRPMakePayment = (RadioButton)findViewById(R.id.btnRPMakePayment);
		spnRPFrequency = (Spinner)findViewById(R.id.spnRPFrequency);
		btnRPMakePayment.setSelected(true);
		
		String[] frequencies = new String[]{ "Daily", "Weekly", "Bi-weekly", "Monthly", "Yearly" };
		ArrayAdapter<String> frequencyadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, frequencies);
		spnRPFrequency.setAdapter(frequencyadapter);
		
		ads = new AccountsDataSource(this);
		tds = new TransactionsDataSource(this);
		
		ads.open();
		List<Account> accounts = ads.getAllAccounts();
		final ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(this, android.R.layout.simple_spinner_dropdown_item, accounts);
		spnRPAccounts.setAdapter(adapter);
		spnRPAccounts.setSelection( 0 );
		ads.close();
    }
}
