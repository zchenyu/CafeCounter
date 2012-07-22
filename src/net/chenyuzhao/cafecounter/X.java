package net.chenyuzhao.cafecounter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.DateFormat;
import android.widget.TextView;

public final class X {
	private X(){}
	
	public static String currencyToString(long dollar, long cent) {
		return String.valueOf(dollar) + "." + (cent < 10 ? "0" : "") + String.valueOf(cent);
	}
	
	public static String currencyToString(long currency) {
		long dollar = currency/100;
		long cent = currency%100;
		return currencyToString(dollar,cent);
	}
	
	public static long stringToCurrency(String s) {
		if( s.contains(".") ) {
			String[] arr = s.split("\\.");
			if(arr[1].length() != 0) {
				return 100*Long.parseLong(arr[0]) + (arr[1].length() == 2 ? 1 : 10)*Long.parseLong(arr[1]);
			} else {
				return 100*Long.parseLong(arr[0]);
			}
		}
		return 100*Long.parseLong(s);
	}
	
	public static long getDollar(String s) {
		return Long.parseLong(s)/100;
	}
	
	public static long getCent(String s) {
		return Long.getLong(s)%100;
	}
	
	public static String timestampToDatestring(long timestamp) {
		Date date = new Date(timestamp);
    	return DateFormat.format("MM/dd/yyyy", date).toString();
	}
	
	public static String datestringToTimestamp(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(date.getTime());
	}
}
