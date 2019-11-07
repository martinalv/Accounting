package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import Model.Account;
import Model.Description;
import Model.Store;
import Model.StoreNames;
import Model.Transaction;

public class FileIO {

	public static Account[] loadAccount(String fileName, StoreNames storeDatabase) throws IOException {

		FileReader statement = new FileReader(fileName);
		BufferedReader buff = new BufferedReader(statement);
		String line;
		String info;
		Description description;
		Transaction transaction;
		StringTokenizer st;
		String[] check;
		String date;
		String[] accountName;
		double amount = 0;
		Account[] account = new Account[2];
		accountName = fileName.split("\\.");
		account[0] = new Account(accountName[0], 13);
		account[1] = new Account(accountName[0], 3);

		while ((line = buff.readLine()) != null) {
			st = new StringTokenizer(line, ",");
			date = st.nextToken();
			amount = Double.valueOf(st.nextToken());
			info = st.nextToken();

			check = info.split(" ");
			// take off comments once you are ready for checks
			if (check[0].equalsIgnoreCase("CHECK") || check[0].equalsIgnoreCase("DEPOSITED")
					|| check[0].equalsIgnoreCase("CASHED") || check[0].equalsIgnoreCase("CUSTOMER")) {

				System.out.println(date + " " + accountName[0]);
				description = getCheckDescription(info, storeDatabase);

			} else {
				description = buildDescription(info, storeDatabase);
			}
			
			transaction = new Transaction(date, accountName[0], Math.abs(amount), description);

			if (amount < 0) {
				account[0].addTransaction(transaction);
			} else {
				transaction.setReceiptCategory();
				account[1].addTransaction(transaction);
			}

		}

		buff.close();

		return account;
	}

	private static Description buildDescription(String info, StoreNames storeDatabase) {
		Description description;
		Store store;

		store = storeDatabase.getStore(info);
		description = new Description(store.getKnownAs(), store.getDescription(), store.getCategory());

		return description;
	}

	public static StoreNames loadStores(String fileName, StoreNames storeDatabase) throws IOException {
		FileReader stores = new FileReader(fileName);
		BufferedReader buff = new BufferedReader(stores);
		Store store;
		String line;
		StringTokenizer st;

		while ((line = buff.readLine()) != null) {
			st = new StringTokenizer(line, ",");
			store = new Store(st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken());
			storeDatabase.addStore(store.getKey(), store);
		}

		buff.close();
		return storeDatabase;
	}

	
	private static Description getCheckDescription(String info, StoreNames storeDatabase) throws IOException{
		Description description;
		String check;
		String name;
		Store store;
		Scanner in = new Scanner(System.in);

		System.out.println("Enter check # or enter 0 if its a cashier's check for the following: \n");
		System.out.println(info + "\n");
		while ((check = in.nextLine().toUpperCase().trim()).isEmpty())
			;
		System.out.println("\nWho's the check made out to?");
		while ((name = in.nextLine().toUpperCase().trim()).isEmpty())
			;

		store = storeDatabase.getStore(name);

		description = new Description(store.getKnownAs(), store.getDescription(), store.getCategory(), check);

		return description;
	}

	public static void saveStoreNames(StoreNames storeDatabase) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("StoreNamesDatabase.txt"));
		Set<String> storeKeys = storeDatabase.getKeySet();
		LinkedList<Store> list;
		Store store;
		for (String key : storeKeys) {
			list = storeDatabase.getStoreList(key);
			for (int i = 0; i < list.size(); i++) {
				store = list.get(i);
				writer.write(String.format("%s,%s,%s\n", store.getName(), store.getKnownAs(), store.getCategory()));
				writer.flush();
			}
		}
		writer.close();

	}
}
