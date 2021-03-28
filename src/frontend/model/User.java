package frontend.model;

public class User {
    //UserInfo(UName varchar2(20) PRIMARY KEY, Password varchar2(20), StreetName varchar2(20), HouseNumber INT DEFAULT 0, City varchar2(20),
    // //PostalCode varchar2(20), Certificate varchar2(20), Budget INT DEFAULT 0)");
    private int houseNumber, budget;
    private String name, password, street, code, certificate, city;

    public User() {
        this.houseNumber = 0;
        this.budget = 0;
        this.name = "";
        this.password = "";
        this.street = "";
        this.code = code;
        this.certificate = "";
        this.city = "";
    }

    public User(String name, String password, String street, int houseNumber, String city, String code, String certificate, int budget) {
        this.houseNumber = houseNumber;
        this.budget = budget;
        this.name = name;
        this.password = password;
        this.street = street;
        this.code = code;
        this.certificate = certificate;
        this.city = city;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
