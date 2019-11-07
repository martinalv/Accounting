package Model;

import java.util.LinkedList;
import java.util.ListIterator;

public class Category {
	private String name;
	private double total;
	private LinkedList<Transaction> transactions;

	public Category(String name) {
		super();
		// TODO Auto-generated constructor stub
		this.name = name;
		total = 0;
		transactions = new LinkedList<Transaction>();
	}

	public void addTransaction(Transaction trans) {
		total += trans.getAmount();
		transactions.add(trans);

	}

	public Transaction getNextTrans() {
		if (!transactions.isEmpty()) {
			return transactions.removeFirst();
		}
		else{
			return null;
		}
	}
	
	public ListIterator<Transaction> getIterator(){
		return transactions.listIterator();
	}
	
	public ListIterator<Transaction> getLastIterator(){
		return transactions.listIterator(transactions.size());
	}

	public double getTotal() {
		return total;
	}

	public String getName() {
		return name;
	}
}
