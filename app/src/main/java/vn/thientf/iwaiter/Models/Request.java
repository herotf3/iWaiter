package vn.thientf.iwaiter.Models;

import java.util.List;

public class Request {
    public static final String STT_Code_PENDING="pending";
    private String userId, desciption;

    public Request(String userId, String tableId, Cart orders, String desciption) {
        this.userId = userId;
        this.tableId = tableId;
        this.orders = orders;
        this.desciption = desciption;
        this.status = STT_Code_PENDING;
    }

    public String getUserId() {
        return userId;
    }

    private String tableId;
    private Cart orders;
    private String status;

    public Request(String tableId, Cart orders) {
        this.tableId = tableId;
        this.orders = orders;
        this.status=STT_Code_PENDING;
    }

    public Request(String userId, String tableId, Cart orders) {
        this.userId = userId;
        this.tableId = tableId;
        this.orders = orders;
        this.status = STT_Code_PENDING;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Cart getOrders() {
        return orders;
    }

    public void setOrders(Cart orders) {
        this.orders = orders;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
