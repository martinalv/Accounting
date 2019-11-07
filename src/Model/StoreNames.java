package Model;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import DataStructures.HashList;

public class StoreNames {
	private HashList<String, Store> names;
	private int randName; 
	private String[] categories = { "AUTO EXPENSES", "DINING & ENTERTAINMENT", "EDUCATION", "REAL PROPERTY", "GROCERY",
			"GEN ADMIN", "LIVING", "PET", "MEDICAL", "ALLOWANCE", "INTEREST","OTHER", "ANNUITIES"};
	
	public StoreNames() {
		super();
		// TODO Auto-generated constructor stub
		randName = (int) Math.round(100.0*Math.random());
		names = new HashList<>(250);

	}

	public void addStore(String key, Store store) {
		names.addData(key, store);
	}

	private void printCategories(){
		for(int i = 0; i < categories.length; i++){
			System.out.println(i + ":\t" + categories[i]);
		}
	}
	

	public LinkedList<Store> getStoreList(String key) {
		return names.getList(key);
	}
	public Set<String> getKeySet(){
		return names.getKeySet();
	}
	public void saveDatabase(){
		
	}
	
	private Store findStore(String key, String info){
		LinkedList<Store> list;
		if ((list = names.getList(key)) != null){
			for(int i = 0; i<list.size(); i++){
				if(info.toUpperCase().contains(list.get(i).getName())){
					return list.get(i);
				}
			}
		}
		
		return null;
	}
	
	public Store getStore(String info){
		StringTokenizer st = new StringTokenizer(info, " */#");
		Store store;
		String key;
		while(st.hasMoreTokens()){
			key = st.nextToken().toUpperCase();
			if( ( store = findStore(key, info) ) != null){
				return store;
			}
			
		}
		
		return makeStore(info);
	}

	
	private Store makeStore(String info){
		Store store;
		Scanner in = new Scanner(System.in);
		String name;
		String knownAs;
		String category;
		String key;
		String description;
		String[] efficiency;
		
		int cat = -1;
		System.out.println("No store found for the following description: \n");
		System.out.println(info);
		
		System.out.println("\nEnter name of store as seen on description.");
		while((name = in.nextLine().toUpperCase().trim()).isEmpty());
		
		efficiency = name.split(" ");
		if(efficiency.length > 1){
			System.out.println("Enter key for name of store as seen on description.");
			while((key = in.nextLine().toUpperCase().trim()).isEmpty());
			
		}
		else{
			key = name;
		}
		
		System.out.println("Is the store known as the name on the statement? Please enter:");
		System.out.println("Y: YES\nN: NO");
		do{
			knownAs = in.nextLine().toUpperCase().trim();
		}while(knownAs.isEmpty() || (!knownAs.equalsIgnoreCase("Y") && !knownAs.equalsIgnoreCase("N")));
		
		if(knownAs.equalsIgnoreCase("N")){
			System.out.println("Enter the name the store is know as.");
			while((knownAs = in.nextLine().toUpperCase().trim()).isEmpty());
		}
		else{
			knownAs = name;
		}
		
		System.out.println("Describe what the store is used for.");
		while((description = in.nextLine().toUpperCase().trim()).isEmpty());
		
	
		printCategories();
		while(cat<0 || cat > (categories.length - 1)){
			System.out.println("Please enter the integer for the category the store is know for.");
			cat = in.nextInt();
		}
		category = categories[cat];
		
		store = new Store(name, key, knownAs, category, description);
		

		System.out.println(String.format("\n\nStore built: \nname: %s, key: %s, knownAs: %s, category: %s, \ndescription:", 
				name, key, knownAs, category, description));
		
		addStore(key, store);
		try {
			writeOutStore(store);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return store;
	}
	
	private void writeOutStore(Store store) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter( Integer.toString(randName) + "CurrentStoreNames.txt", true));
		BufferedWriter continous = new BufferedWriter(new FileWriter( "continousStoreNames.txt", true));
		writer.append(String.format("%s,%s,%s,%s,%s\n", store.getName(), store.getKey(), store.getKnownAs(), store.getCategory(), store.getDescription()));
		continous.append(String.format("%s,%s,%s,%s,%s\n", store.getName(), store.getKey(), store.getKnownAs(), store.getCategory(), store.getDescription()));
		writer.flush();
		writer.close();
		continous.flush();
		continous.close();
	}
}
