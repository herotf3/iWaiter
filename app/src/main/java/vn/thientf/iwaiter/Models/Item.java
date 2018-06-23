package vn.thientf.iwaiter.Models;

public class Item {
    public Food food;
    public int qty;

    public Item(Food food, int qTy) {
        this.food = food;
        this.qty = qTy;
    }
}
