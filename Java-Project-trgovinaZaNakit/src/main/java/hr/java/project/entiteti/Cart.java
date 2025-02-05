package hr.java.project.entiteti;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private List<Product> productList;
    private String userID;

    public Cart() {
        productList = new ArrayList<>();
    }

    public Cart(List<Product> productList, String userID) {
        this.productList = productList;
        this.userID = userID;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

