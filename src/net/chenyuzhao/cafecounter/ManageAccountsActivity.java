package net.chenyuzhao.cafecounter;

import java.util.List;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class ManageAccountsActivity extends CCActivity {
	private ListView lvAccounts;
	private Button btnNewAccount;
	private AccountsDataSource ds;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_accounts_layout);
        
        lvAccounts = (ListView)findViewById(R.id.lvAccounts);
        btnNewAccount = (Button)findViewById(R.id.btnNewAccount);
        
        ds = new AccountsDataSource(this);
        ds.open();
        List<Account> accounts = ds.getAllAccounts();
        final ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(this, android.R.layout.simple_list_item_1, accounts);
        lvAccounts.setAdapter(adapter);
        ds.close();
        
        lvAccounts.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Account account = adapter.getItem(position);
				Intent intent = new Intent(ManageAccountsActivity.this, EditAccountActivity.class);
				intent.putExtra("account_id", account.id);
				startActivity(intent);
			}
		});
        
        btnNewAccount.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ManageAccountsActivity.this, NewAccountActivity.class);
				startActivity(intent);
			}
		});
    }
}
