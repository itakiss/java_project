package hr.java.project.entiteti;

import java.time.LocalDate;

public class CartItem {
    private Product product;

    public CartItem(Product product) {
        this.product = product;

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}