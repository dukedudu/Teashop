package frontend.model;

public class User {
<<<<<<< HEAD
	//private final int id;
	private int house, budget;
	private String name, password, street, code, certificate;
	public  User() {
		//this.id = 0;
		this.password = "";
=======
	private int house, budget;
	private String name, password, street, code, certificate;
	public  User() {
>>>>>>> fe9904b8ad4b25e7ca3410c3f7c802712d327e7f
		this.name = "";
		this.password = "";
		this.street = "";
		this.house = 0;
		this.code = "";
		this.budget = 0;
		this.certificate = "";
	}

	public User(String name, String password, String code, String street, int house, String certificate, int budget ) {
<<<<<<< HEAD
		//this.id = id;
		this.password = password;
=======
>>>>>>> fe9904b8ad4b25e7ca3410c3f7c802712d327e7f
		this.name = name;
		this.password = password;
		this.street = street;
		this.house = house;
		this.code = code;
		this.budget = budget;
		this.certificate = certificate;
	}
<<<<<<< HEAD
	
//	public int getId() {
//		return id;
//	}
=======
>>>>>>> fe9904b8ad4b25e7ca3410c3f7c802712d327e7f

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) { this.street = street; }

	public int getHouse() {
		return house;
	}

	public void setHouse(int house) { this.house = house; }

	public String getCode() {
		return code;
	}

	public void setCode(String code) { this.code = code; }

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) { this.budget = budget; }

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) { this.certificate = certificate; }
}
