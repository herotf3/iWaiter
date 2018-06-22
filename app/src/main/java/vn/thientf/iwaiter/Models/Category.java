package vn.thientf.iwaiter.Models;

/**
 * Created by ASUS on 22/06/2018.
 */

public class Category {
    String name, resId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public Category() {

    }

    public Category(String name, String resId) {

        this.name = name;
        this.resId = resId;
    }
}
