package Model;

public class Description {
	private String name;
	private String description;
	private String category;
	private String type;
	
	public Description(String name, String description, String category){
		super();
		
		this.name = name;
		this.description = description;
		this.category = category;
		this.type = "EFT";

	}
	
	public Description(String name, String description, String category, String number){
		super();
		
		this.name = name;
		this.description = description;
		this.category = category;
		if(Integer.valueOf(number) == 0){
			type = "CASHIER'S CHECK";
		}
		else{
			this.type = number;
		}
		

	}

	public void setStore(Store store){
		name = store.getKnownAs();
		description = store.getDescription();
		category = store.getCategory();
	}
	
	public String getName(){
		return name;
	}
	
	public String getCategory(){
		return category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
	
}
