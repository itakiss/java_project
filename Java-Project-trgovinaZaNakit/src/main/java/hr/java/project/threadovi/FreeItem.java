package hr.java.project.threadovi;

import hr.java.project.entiteti.Cart;
import hr.java.project.entiteti.Product;

public class FreeItem implements Runnable {

    private final Cart cart;
    private final Product product;

    public FreeItem(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
    }

    @Override
    public void run() {

    }
}