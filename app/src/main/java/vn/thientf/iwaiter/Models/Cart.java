package vn.thientf.iwaiter.Models;

import java.util.Map;

/**
 * Created by ASUS on 21/06/2018.
 */

public class Cart {
    Map<String, Item> items; //<foodId,Item(Food,qty)>

    public void addItem(Food food) {
        if (!items.containsKey(food.getId())) {
            items.put(food.getId(), new Item(food, 1));
        } else {
            items.get(food.getId()).qty++;
        }
    }

    public void addItem(Food food, int qty) {
        if (!items.containsKey(food.getId())) {
            items.put(food.getId(), new Item(food, qty));
        } else {
            items.get(food.getId()).qty += qty;
        }
    }

    public int getSubTotal() {
        int res = 0;
        for (Map.Entry<String, Item> entry : items.entrySet()) {
            res += entry.getValue().qty * entry.getValue().food.getPrice();
        }
        return res;
    }
}

class Item {
    Food food;
    int qty;

    public Item(Food food, int qTy) {
        this.food = food;
        this.qty = qTy;
    }
}
