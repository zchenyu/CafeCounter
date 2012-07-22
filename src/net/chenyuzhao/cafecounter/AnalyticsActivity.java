package net.chenyuzhao.cafecounter;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class AnalyticsActivity extends CCActivity {
	private ListView lvAccounts;
	private AccountsDataSource ds;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analytics_layout);
        
        lvAccounts = (ListView)findViewById(R.id.lvAccounts);
        
        ds = new AccountsDataSource(this);
        ds.open();
        List<Account> accounts = ds.getAllAccounts();
        final ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(this, android.R.layout.simple_list_item_1, accounts);
        lvAccounts.setAdapter(adapter);
        ds.close();
        
        lvAccounts.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Account account = adapter.getItem(position);
				Intent intent = new Intent(AnalyticsActivity.this, AccountAnalyticsActivity.class);
				intent.putExtra("account_id", account.id);
				startActivity(intent);
			}
		});
    }
}
