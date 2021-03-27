package frontend.model;

public class Recipe {
    private String name, tea, kind;
    private int pearl, jelly, lemon, orange, calories;

    public Recipe() {
        this.name = "";
//        this.tea = "";
        this.pearl = 0;
        this.jelly = 0;
        this.lemon = 0;
        this.orange = 0;
        this.calories = 0;
    }

    public Recipe(String name, String tea, String kind, int pearl, int jelly, int lemon, int orange, int calories) {
        this.name = name;
        this.tea = tea;
        kind = kind;
        this.pearl = pearl;
        this.jelly = jelly;
        this.lemon = lemon;
        this.orange = orange;
        this.calories = calories;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        kind = kind;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getTea() { return tea; }
//
//    public void setTea(String tea) { this.tea = tea; }

    public int getPearl() { return pearl; }

    public void setPearl(int pearl) { this.pearl = pearl; }

    public int getJelly() { return jelly; }

    public void setJelly(int jelly) { this.jelly = jelly; }

    public int getLemon() { return lemon; }

    public void setLemon(int lemon) { this.lemon = lemon; }

    public int getOrange() { return orange; }

    public void setOrange(int orange) { this.orange = orange; }
    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }
}
