package frontend.model;

public class User {
	private final int id, house, budget;
	private final String name, street, city, code, certificate;

	public  User() {
		this.id = 0;
		this.name = "";
		this.street = "";
		this.house = 0;
		this.city = "";
		this.code = "";
		this.budget = 0;
		this.certificate = "";
	}

	public User(int id, String name, String street, int house, String city, String code, int budget, String certificate) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.house = house;
		this.city = city;
		this.code = code;
		this.budget = budget;
		this.certificate = certificate;
	}
	
	public int getId() {
		return id;
	}

	public String getName() { return name; }

	public void setName(String name) { name = name; }

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) { street = street; }

	public int getHouse() {
		return house;
	}

	public void setHouse(int house) { house = house; }

	public String getCity() {
		return city;
	}

	public void setCity(String city) { city = city; }

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
