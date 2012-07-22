package net.chenyuzhao.cafecounter;

public class Account {
	long id;
	String name;
	long balance;
	
	public Account(String name, long balance) {
		this.id = -1;
		this.name = name;
		this.balance = balance;
	}
	
	public Account(long id, String name, long balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}
	
	public String toString() {
		return name + " ($" + X.currencyToString(balance) + ")";
	}
}
