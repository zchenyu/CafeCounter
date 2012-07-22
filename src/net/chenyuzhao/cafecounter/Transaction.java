package net.chenyuzhao.cafecounter;

public class Transaction {
	long id;
	long amount;
	String name, address, description;
	long timestamp;
	long account_id;
	
	public Transaction( long id, long amount, String name, String address, String description, long timestamp, long account_id ) {
		this.id = id;
		this.amount = amount;
		this.name = name;
		this.address = address;
		this.description = description;
		this.timestamp = timestamp;
		this.account_id = account_id;
	}
	
	public String toString() {
		return "Transaction ID: " + id;
	}
}
