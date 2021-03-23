package ca.ubc.cs304.model;

public class User {
	private final int id;
	private final String name, street, house, city, code;

	public  User() {
		this.id = 0;
		this.name = "";
		this.street = "";
		this.house = "";
		this.city = "";
		this.code = "";
	}

	public User(int id, String name, String street, String house, String city, String code) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.house = house;
		this.city = city;
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) { id = id; }

	public String getName() {
		return name;
	}

	public void setName(String name) { name = name; }

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) { street = street; }

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) { house = house; }

	public String getCity() {
		return city;
	}

	public void setCity(String city) { city = city; }

	public String getCode() {
		return code;
	}

	public void setCode(String code) { code = code; }
}
