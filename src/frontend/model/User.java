package frontend.model;

public class User {
	private final int id;
	private int house, budget;
	private String name, password, street, code, certificate;
	public  User() {
		this.id = 0;
		this.password = "";
		this.name = "";
		this.street = "";
		this.house = 0;
		this.code = "";
		this.budget = 0;
		this.certificate = "";
	}

	public User(int id, String name, String password, String code, String street, int house, String certificate, int budget ) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.street = street;
		this.house = house;
		this.code = code;
		this.budget = budget;
		this.certificate = certificate;
	}
	
	public int getId() {
		return id;
	}

	public String getName() { return name; }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password){
		password = password;
	}

	public void setName(String name) { name = name; }

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) { street = street; }

	public int getHouse() {
		return house;
	}

	public void setHouse(int house) { house = house; }

	public String getCode() {
		return code;
	}

	public void setCode(String code) { code = code; }

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) { budget = budget; }

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) { certificate = certificate; }
}
