package frontend.model;

public class User {
    private String name, password, street, city, code;
    private int houseNumber;

    public User() {
        this.name = "";
        this.password = "";
        this.street = "";
        this.houseNumber = 0;
        this.city = "";
        this.code = "";
    }

    public User(String name, String password, String street, int houseNumber, String city, String code) {
        this.name = name;
        this.password = password;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
