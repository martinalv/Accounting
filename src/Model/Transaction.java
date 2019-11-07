package Model;

public class Transaction {
	
	//date array = { month, day, year}
	private int[] date;
	private String accountName;
	private double amount;
	private Description description;

	

	//flags that need users input or explanation
	//flags arr = { large purchase (L), Unkown (U)}

	//flag for moms Transfer allawance
	private boolean flags[];

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(String sDate, String accountName,double amount, Description description) {
		super();
		date = new int[3];
		String[] d = sDate.split("/");
		
		for(int i = 0; i < date.length; i++){
			date[i] = Integer.parseInt(d[i]);
		}
		this.accountName = accountName;
		this.amount = amount;
		this.description = description;
//		this.flags = flags;
	}
	
	
	
	public void setDescription(Description description){
		this.description = description;
	}
	
	public String getCategory(){
		return description.getCategory();
	}
	public String getName(){
		return description.getName();
	}
	public String getDescription(){
		return description.getDescription();
	}
	public double getAmount(){
		return amount;
	}
	public int[] getDate(){
		return date;
	}
	public String getAccountName(){
		return accountName;
	}
	public void setReceiptCategory(){
		String[] cat = {"INTEREST", "ANNUITIES", "OTHER"};
		boolean checker = false;
		for(int i = 0; i < cat.length; i++){
			if(cat[i].equalsIgnoreCase(description.getCategory())){
				checker = true;
			}
		}
		
		if(!checker){
			description.setCategory("OTHER");
		}
		
		
	}
	public String[] printTransaction(){
		String[] print = new String[6];
		print[0] = String.format("%d/%d/%d", date[0], date[1], date[2]);
		print[1] = accountName;
		print[2] = description.getType();
		print[3] = description.getCategory();
		print[4] = String.format("%s - %s", description.getName(), description.getDescription());
		print[5] = String.format("%.2f", amount);
		
		return print;
	}
	
}
