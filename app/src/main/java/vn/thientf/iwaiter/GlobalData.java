package vn.thientf.iwaiter;

import vn.thientf.iwaiter.Models.Cart;

/**
 * Created by ASUS on 21/06/2018.
 */
//singleton class
public class GlobalData {
    private static GlobalData instance;
    private String currTable, currRes;
    private Cart currCart;

    public String getCurrTable() {
        return currTable;
    }

    public void setCurrTable(String currTable) {
        this.currTable = currTable;
    }

    public String getCurrRes() {
        return currRes;
    }

    public void setCurrRes(String currRes) {
        this.currRes = currRes;
    }

    public Cart getCurrCart() {
        return currCart;
    }

    public void setCurrCart(Cart currCart) {
        this.currCart = currCart;
    }

    private GlobalData() {
        currRes = currTable = null;
        currCart = null;
    }

    public static GlobalData getInstance(){
        if (instance==null)
            instance=new GlobalData();
        return instance;
    }
}
