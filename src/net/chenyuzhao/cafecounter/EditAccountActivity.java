package net.chenyuzhao.cafecounter;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;

public class EditAccountActivity extends CCActivity {
	AccountsDataSource ads;
	TransactionsDataSource tds;

	long id;
	EditText txtAccountName, txtCurrentBalance, txtAddFunds, txtNewBalance;
	Button btnEditAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_layout);
        
        id = getIntent().getLongExtra("account_id", -1);
        txtAccountName = (EditText)findViewById(R.id.txtAccountName);
        txtCurrentBalance = (EditText)findViewById(R.id.txtCurrentBalance);
        txtAddFunds = (EditText)findViewById(R.id.txtAddFunds);
        txtNewBalance = (EditText)findViewById(R.id.txtNewBalance);
        btnEditAccount = (Button)findViewById(R.id.btnEditAccount);
        
        ads = new AccountsDataSource(this);
        ads.open();
        Account account = ads.getAccount(id);
        txtAccountName.setText(account.name);
        txtCurrentBalance.setText(X.currencyToString(account.balance));
        txtNewBalance.setText(X.currencyToString(account.balance));
        ads.close();
        
        tds = new TransactionsDataSource(this);
        
        txtAddFunds.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			public void afterTextChanged(Editable s) {
				long a = X.stringToCurrency(txtCurrentBalance.getText().toString());
				long b = X.stringToCurrency(txtAddFunds.getText().toString());
				txtNewBalance.setText(X.currencyToString(a+b));
			}
		});
        btnEditAccount.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(EditAccountActivity.this)
					.setTitle("Confirm Account Changes")
					.setMessage("Are you sure you want to apply changes to this account?")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							ads.open();
							ads.updateAccount(new Account(id,txtAccountName.getText().toString(),X.stringToCurrency(txtNewBalance.getText().toString())));
							ads.close();
							
							long funds = X.stringToCurrency(txtAddFunds.getText().toString());
							
							if( funds != 0) {
								tds.open();
								String amount = String.valueOf(funds);
								String name = funds > 0 ? "Added funds" : "Removed funds";
								String timestamp = String.valueOf(System.currentTimeMillis());
								String account_id = String.valueOf(id);
								tds.createTransaction(amount, name, "", "", timestamp, account_id);
								tds.close();
							}
							
							Intent intent = new Intent(EditAccountActivity.this,ManageAccountsActivity.class);
							startActivity(intent);
						}
					})
					.setNegativeButton(android.R.string.no, null)
					.show();
			}
		});
    }
}
