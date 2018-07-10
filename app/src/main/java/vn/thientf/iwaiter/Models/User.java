package vn.thientf.iwaiter.Models;

import com.google.firebase.auth.FirebaseUser;

public class User {
    private String name, phone, address;
    boolean isAdmin = false;

    public User() {
    }

    public User(FirebaseUser user) {
        name = user.getDisplayName();
        phone = user.getPhoneNumber();
        address = "";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(String name, String phone, String address) {

        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
