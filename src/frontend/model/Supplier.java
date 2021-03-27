package frontend.model;

public class Supplier { // combine supplier entity with supplies
    private String CompanyName, GName;

    public Supplier(String companyName, String GName) {
        CompanyName = companyName;
        this.GName = GName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getGName() {
        return GName;
    }

    public void setGName(String GName) {
        this.GName = GName;
    }
}
