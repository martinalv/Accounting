package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

public class Account {
	// Name of bank account
	private String name;
	private double total;
	// add subsections IN, OUT: categories, chronological order
	// OUT

	private Hashtable<String, Category> categorical;
	private LinkedList<Transaction> chronological;

	public Account(String name, int categorySize) {
		super();
		// TODO Auto-generated constructor stub
		this.name = name;
		total = 0;
		categorical = new Hashtable<>(((int) Math.ceil(categorySize + categorySize * .75)));
		chronological = new LinkedList<Transaction>();
	}

	public void addTransaction(Transaction trans) {
		String cat = trans.getCategory();
		Category category;

		total += trans.getAmount();

		if ((category = categorical.get(cat)) == null) {
			category = new Category(cat);
		}
		category.addTransaction(trans);
		categorical.put(cat, category);
		chronological.add(trans);

	}

	public Category getCategory(String category) {
		return categorical.get(category);
	}

	public Transaction getNextChron() {
		if (!chronological.isEmpty()) {
			return chronological.removeFirst();
		} else {
			return null;
		}
	}

	public double getTotal() {
		return total;
	}

	public String getName() {
		return name;
	}

	public void tempPrintByCat() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("outputCat.csv"));
		Set<String> categoryKeys = categorical.keySet();
		Iterator it;
		Category category;
		Transaction trans;
		String[] print;

		for (String key : categoryKeys) {
			category = categorical.get(key);
			it = category.getIterator();
			while (it.hasNext()) {
				trans = (Transaction) it.next();
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

	public void tempPrintChron() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("outputChron.csv"));
		Transaction trans;
		String[] print;

		for (int i = 0; i < chronological.size(); i++) {
			print = chronological.get(i).printTransaction();
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

	public ListIterator<Transaction> getChronIter() {

		if (chronological.listIterator().hasNext()) {
			return chronological.listIterator();
		} else {
			return null;
		}
	}

	public ListIterator<Transaction> getCatIterator(String cat) {
		Category category = categorical.get(cat);

		if (category != null) {
			if (category.getIterator().hasNext()) {
				return category.getIterator();
			}
		}
		return null;
	}

}
