package vn.thientf.iwaiter.Models;

import java.util.List;

public class Request {
    public static final String STT_Code_PENDING="pending";
    private String tableId;
    private Cart orders;
    private String status;

    public Request(String tableId, Cart orders) {
        this.tableId = tableId;
        this.orders = orders;
        this.status=STT_Code_PENDING;
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
}
