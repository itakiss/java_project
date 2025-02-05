package hr.java.project.threadovi;

import hr.java.project.entiteti.Cart;
import hr.java.project.entiteti.Product;

public class AddItem implements Runnable {

    private final Cart cart;
    private final Product product;
    private final int quantity;

    public AddItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public void run() {

    }
}
