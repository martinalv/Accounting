package Adaptor;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import Model.Account;
import Model.ActionType;
import Model.StoreNames;
import Util.FileIO;

public abstract class proxyAccounting {
	private static LinkedList<Account[]> accounts;
	private static StoreNames storeDatabase;
	private static ActionType disbursements;
	private static ActionType receipts;
	private static String[] disbursementCategories = { "AUTO EXPENSES", "DINING & ENTERTAINMENT", "EDUCATION",
			"REAL PROPERTY", "GROCERY", "GEN ADMIN", "LIVING", "PET", "MEDICAL", "ALLOWANCE", "INTEREST", "OTHER",
			"ANNUITIES" };
	private static String[] recieptCategories = { "INTEREST", "ANNUITIES", "OTHER" };
	private static String printDirectory;

	public void construct() {
		accounts = new LinkedList<Account[]>();
	}

	public void loadSavedData(String filename) {
		storeDatabase = new StoreNames();
		try {
			FileIO.loadStores("continousStoreNames.txt", storeDatabase);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addAcct(String fileName) {
		Account[] acct;
		try {
			acct = FileIO.loadAccount(fileName, storeDatabase);
			accounts.add(acct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void calculate() {
		int size = accounts.size();
		Account[] acct;
		Account[] dis = new Account[size];
		Account[] rec = new Account[size];
		for (int i = 0; i < size; i++) {
			acct = accounts.get(i);
			dis[i] = acct[0];
			rec[i] = acct[1];
		}

		receipts = new ActionType("Receipts", recieptCategories, rec);
		disbursements = new ActionType("Disbursements", disbursementCategories, dis);

	}


	public void setPrintDirectory(String path) {
		printDirectory = path + "Accouting" +String.valueOf(LocalDate.now());
		File directory = new File(printDirectory);
		if(!directory.exists()){
			directory.mkdir();
		}
	}

	public void printAll() {
		printDisbursements();
		printReciepts();
	}

	public void printDisbursements() {
		try {
			disbursements.printChron(printDirectory);
			disbursements.printByCat(printDirectory);
			disbursements.printSummary(printDirectory);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void printReciepts() {
		try {
			receipts.printChron(printDirectory);
			receipts.printByCat(printDirectory);
			receipts.printSummary(printDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
