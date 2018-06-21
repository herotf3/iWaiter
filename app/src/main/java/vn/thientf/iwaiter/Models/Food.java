package vn.thientf.iwaiter.Models;

/**
 * Created by ASUS on 21/06/2018.
 */

public class Food {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAvalable() {
        return isAvalable;
    }

    public void setAvalable(boolean avalable) {
        isAvalable = avalable;
    }

    public Food() {

    }

    String Name;
    int Price;
    String image;
    boolean isAvalable;
}
