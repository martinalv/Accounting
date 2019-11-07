package Model;

public class Store {
	private String name;
	private String key;
	private String knownAs;
	private String category;
	private String description;
	
	public Store() {
		super();	
	}
	
	public Store(String name, String key, String knownAs, String category, String description) {
		super();
		this.name = name;
		this.key = key;
		this.knownAs = knownAs;
		this.category = category;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKnownAs() {
		return knownAs;
	}

	public void setKnownAs(String knownAs) {
		this.knownAs = knownAs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
