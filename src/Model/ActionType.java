package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

public class ActionType {
	private String name;
	private double total;
	private Account[] accounts;
	private String[] categories;
	private Hashtable<String, Category> categorical;
	private LinkedList<Transaction> chronological;

	public ActionType(String name, String[] categories, Account[] accounts) {
		super();
		// TODO Auto-generated constructor stub
		this.name = name;
		this.categories = categories;
		total = 0;
		this.accounts = accounts;
		categorical = new Hashtable<>(((int) Math.ceil(categories.length + categories.length * .75)));
		chronological = new LinkedList<Transaction>();
		buildChronologically();
		buildByCategory();

	}

	private void buildChronologically() {
		Transaction transaction;
		LinkedList<ListIterator<Transaction>> it = getChronIter();
		do{
			transaction = getNextTrans(it);
			chronological.add(transaction);
			updateIterator(it);
		}while(!iteratorIsEmpty(it));
	}
	
	private void buildByCategory(){
		Transaction transaction;
		Category category;
		LinkedList<ListIterator<Transaction>> it;
		for(int i = 0; i< categories.length; i++){
			it = getCatIter(categories[i]);
			if(!iteratorIsEmpty(it)){
				category = new Category(categories[i]);
				do{
					transaction = getNextTrans(it);
					category.addTransaction(transaction);
					
					total+= transaction.getAmount();
					
					updateIterator(it);
				}while(!iteratorIsEmpty(it));
				categorical.put(categories[i], category);
			}
		}
	}
	
	private LinkedList<ListIterator<Transaction>> getCatIter(String cat) {
		LinkedList<ListIterator<Transaction>> iterator = new LinkedList<ListIterator<Transaction>>();
		ListIterator<Transaction> it;
		for(int i = 0; i < accounts.length; i++){
			if((it = accounts[i].getCatIterator(cat)) != null){
					iterator.add(it);
			}
		}
		
		return iterator;
	}
	private LinkedList<ListIterator<Transaction>> getChronIter() {
		LinkedList<ListIterator<Transaction>> iterator = new LinkedList<ListIterator<Transaction>>();
		ListIterator<Transaction> it;
		for (int i = 0; i < accounts.length; i++) {
			if((it = accounts[i].getChronIter()) != null){
				iterator.add(it);
			}
			
		}

		return iterator;
	}

	private boolean iteratorIsEmpty(LinkedList<ListIterator<Transaction>> it){
		if(it.size() > 0 ){
			return false;
		}
		else{
			return true;
		}
	}
	
	private void updateIterator(LinkedList<ListIterator<Transaction>> it) {
		for (int i = 0; i < it.size(); i++) {
			if (!it.get(i).hasNext()) {
				it.remove(i);
			}
		}
	}

	private Transaction[] getTrans(LinkedList<ListIterator<Transaction>> it) {
		int size = it.size();
		Transaction[] transactions = new Transaction[size];
		for (int i = 0; i < size; i++) {
			transactions[i] = it.get(i).next();
		}

		return transactions;
	}

	private int[][] getDates(Transaction[] transactions) {
		int size = transactions.length;
		int[][] dates = new int[size][3];
		for (int i = 0; i < size; i++) {
			if (transactions[i] != null) {
				dates[i] = transactions[i].getDate();
			} else {
				dates[i] = null;
			}
		}

		return dates;
	}

	private int getNextIndex(int[][] dates) {
		int index = 0;
		for (int i = 1; i < dates.length; i++) {
			if (dates[index][2] < dates[i][2]) {
				index = i;
				break;
			} else if (dates[index][2] == dates[i][2]) {
				if (dates[index][0] < dates[i][0]) {
					index = i;
					break;
				} else if (dates[index][0] == dates[i][0]) {
					if (dates[index][1] < dates[i][1]) {
						index = i;
					}
				}
			}

		}

		return index;
	}

	private void resetIterators(LinkedList<ListIterator<Transaction>> it, int exceptionIndex) {
		for (int i = 0; i < it.size(); i++) {
			if (i != exceptionIndex) {
				it.get(i).previous();
			}
		}

	}

	private Transaction getNextTrans(LinkedList<ListIterator<Transaction>> it) {
		Transaction[] transactions = getTrans(it);
		int[][] dates = getDates(transactions);
		int index = getNextIndex(dates);
		resetIterators(it, index);
		return transactions[index];
	}
	
	//prints from oldest to newest
	public void printChron(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + name +"Chronological.csv"));
		Transaction trans;
		String[] print;
		ListIterator<Transaction> it;
		it = chronological.listIterator(chronological.size());
		while(it.hasPrevious()){
			trans = it.previous();
			print = trans.printTransaction();
			for (int j = 0; j < print.length; j++) {
				writer.write("\"" + print[j] + "\"");
				if (j < print.length - 1) {
					writer.write(",");
				}
			}
			writer.write("\n");
		}
		writer.flush();
		writer.close();

	}


	//prints from oldest to newest
	public void printByCat(String path) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + name + "Category.csv"));
		Set<String> categoryKeys = categorical.keySet();
		ListIterator<Transaction> it;
		Category category;
		Transaction trans;
		String[] print;
		
		for (String key : categoryKeys) {
			category = categorical.get(key);
			it = category.getLastIterator();
			while (it.hasPrevious()) {
				trans = it.previous();
				print = trans.printTransaction();
				for (int i = 0; i < print.length; i++) {
					writer.write("\"" + print[i] + "\"");
					if (i < print.length - 1) {
						writer.write(",");
					}
				}

				writer.write("\n");
			}
			writer.flush();
		}
		writer.close();

	}
	
	public void printSummary(String path) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + name + "Summary.csv"));
		Set<String> categoryKeys = categorical.keySet();
		Category category;
		double catTotal = 0;
		double accTotal = 0;
		
		writer.write("\"TOTAL\",\"" + String.format("%.2f", total) + "\"\n");
		writer.write("\n\n");
		
		for (String key : categoryKeys) {
			category = categorical.get(key);
			writer.write("\"" + category.getName() + "\",\""+ String.format("%.2f", category.getTotal()) + "\"\n");
			catTotal += category.getTotal();
			
		}
		writer.write("\"TOTAL\",\"" + String.format("%.2f", catTotal) + "\"\n");
		writer.write("\n\n");
		
		for(int i = 0; i < accounts.length; i++){
			writer.write("\"" + accounts[i].getName() + "\",\"" + String.format("%.2f", accounts[i].getTotal()) + "\"\n");
			accTotal += accounts[i].getTotal();
		}
		writer.write("\"TOTAL\",\"" + String.format("%.2f", accTotal) + "\"\n");

		writer.flush();
		writer.close();

	}
}
