package net.chenyuzhao.cafecounter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class NewAccountActivity extends CCActivity {
	AccountsDataSource ds;
	
	EditText txtNewAccountName, txtInitialBalance;
	Button btnCreateNewAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account_layout);
        
        txtNewAccountName = (EditText)findViewById(R.id.txtNewAccountName);
        txtInitialBalance = (EditText)findViewById(R.id.txtInitialBalance);
        btnCreateNewAccount = (Button)findViewById(R.id.btnCreateNewAccount);
        
        ds = new AccountsDataSource(this);
        
        btnCreateNewAccount.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ds.open();
				ds.insertAccount(new Account(txtNewAccountName.getText().toString(), X.stringToCurrency(txtInitialBalance.getText().toString())));
				ds.close();
				
				Intent intent = new Intent(NewAccountActivity.this,ManageAccountsActivity.class);
				startActivity(intent);
			}
		});
    }
}
