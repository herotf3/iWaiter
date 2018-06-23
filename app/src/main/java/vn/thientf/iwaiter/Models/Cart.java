package vn.thientf.iwaiter.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 21/06/2018.
 */

public class Cart {
    public Map<String, Item> items; //<foodId,Item(Food,qty)>
    private static Cart instance;
    public static Cart getInstance(){
        if (instance==null)
            instance=new Cart();
        return instance;
    }

    private Cart(){
        items=new HashMap<String,Item>();
    }

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

    public void clear() {
        items.clear();
    }
}

