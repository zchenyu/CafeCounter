package net.chenyuzhao.cafecounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public abstract class CCActivity extends Activity {
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
    	
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.new_transaction:
            	intent = new Intent(this, EditTransactionActivity.class);
                startActivity(intent);
            	break;
            case R.id.manage_accounts:
            	intent = new Intent(this, ManageAccountsActivity.class);
                startActivity(intent);
            	break;
            case R.id.analytics:
            	intent = new Intent(this, AnalyticsActivity.class);
                startActivity(intent);
            	break;
        }
        return true;
    }
}
