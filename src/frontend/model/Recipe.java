package frontend.model;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

public class Recipe {
    private final int id, pearl, jelly, lemon, orange, calories;
    private final String name, tea;

    public Recipe() {
        this.id = 0;
        this.name = "";
        this.tea = "";
        this.pearl = 0;
        this.jelly = 0;
        this.lemon = 0;
        this.orange = 0;
        this.calories = 0;
    }

    public Recipe(int id, String name, String tea, int pearl, int jelly, int lemon, int orange, int calories) {
        this.id = id;
        this.name = name;
        this.tea = tea;
        this.pearl = pearl;
        this.jelly = jelly;
        this.lemon = lemon;
        this.orange = orange;
        this.calories = calories;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { name = name; }

    public String getTea() { return tea; }

    public void setTea(String tea) { tea = tea; }

    public int getPearl() { return pearl; }

    public void setPearl(int pearl) { pearl = pearl; }

    public int getJelly() { return jelly; }

    public void setJelly(int jelly) { jelly = jelly; }

    public int getLemon() { return lemon; }

    public void setLemon(int lemon) { lemon = lemon; }

    public int getOrange() { return orange; }

    public void setOrange(int orange) { orange = orange; }
}
