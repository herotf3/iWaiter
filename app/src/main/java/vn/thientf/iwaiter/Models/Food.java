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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Food() {

    }

    public Food(String id, String name, int price, String image, boolean isAvalable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.isAvailable = isAvalable;
    }

    private String name;
    private int price;
    private String image;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    private String categoryId;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    private boolean isAvailable;
}
