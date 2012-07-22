package net.chenyuzhao.cafecounter;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.androidplot.xy.*;
import com.androidplot.series.*;

public class AccountAnalyticsActivity extends CCActivity {
	long id;
	XYPlot gphBalance;
	
	AccountsDataSource ads;
	TransactionsDataSource tds;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_analytics_layout);

        id = getIntent().getLongExtra("account_id", -1);
        gphBalance = (XYPlot)findViewById(R.id.gphBalance);
        
        ads = new AccountsDataSource(this);
        ads.open();
        Account account = ads.getAccount(id);
        ads.close();
        
        tds = new TransactionsDataSource(this);
        tds.open();
        List<Transaction> transactions = tds.getAllTransactions();
        accountFilter(transactions,id);
        sortTimeDesc(transactions);
        tds.close();
        
        LinkedList<Long> days = new LinkedList<Long>();
        LinkedList<Number> balances = new LinkedList<Number>();
        
        double balance = account.balance/100.0;
        days.addFirst(System.currentTimeMillis());
        balances.addFirst(balance);
        
        for(Transaction t : transactions) {
        	balance -= t.amount/100.0;
        	days.addFirst(t.timestamp);
        	balances.addFirst(balance);
        }
        
        XYSeries series = new SimpleXYSeries(days, balances, "Balance");
        LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.rgb(0, 200, 0), Color.rgb(0, 100, 0), Color.rgb(0, 50, 0));
        
        gphBalance.addSeries(series, seriesFormat);
        gphBalance.setDomainLabel("Date");
        gphBalance.setRangeLabel("Balance");
        gphBalance.setRangeValueFormat(new DecimalFormat("0"));
        gphBalance.setDomainValueFormat(new Format() {
        	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        	
        	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        		long timestamp = ((Number)obj).longValue();
        		Date date = new Date(timestamp);
        		return dateFormat.format(date, toAppendTo, pos);
			}

			public Object parseObject(String source, ParsePosition pos) {
				return null;
			}
        });
        gphBalance.setRangeBottomMax(0);
        gphBalance.setRangeTopMin(2000);
        gphBalance.disableAllMarkup();
        
    }
    
    private void accountFilter(List<Transaction> list, long id) {
    	for(ListIterator<Transaction> iter = list.listIterator(); iter.hasNext(); ) {
    		Transaction t = iter.next();
    		if(t.account_id != id) {
    			iter.remove();
    		}
    	}
    }
    
    private void sortTimeDesc(List<Transaction> list) {
    	Collections.sort(list, new Comparator<Transaction>() {
			public int compare(Transaction lhs, Transaction rhs) {
				return (int)(rhs.timestamp-lhs.timestamp);
			}
    	});
    }
}
