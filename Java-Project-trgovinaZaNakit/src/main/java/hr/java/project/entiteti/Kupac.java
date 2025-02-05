package hr.java.project.entiteti;


import java.io.Serializable;

public class Kupac extends User implements Serializable {

    private String email;
    private int orders;
    private String status;
    private String id;

    private String gender;
    Cart cart;

    public Kupac(Builder builder, Cart cart) {
        super(builder.name, builder.surname, builder.id, builder.role, builder.password);
        this.email = builder.email;
        this.orders = builder.orders;
        this.status = builder.status;
        this.id = builder.id;
        this.gender=builder.gender;
        this.cart=cart;
    }
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static class Builder {
        private String name;
        private String surname;
        private String id;
        private String role;
        private String password;
        private String email;
        private int orders;
        private String status;
        private String gender;
        private Cart cart;

        public Builder(String name, String surname, String id, String role, String password) {
            this.name = name;
            this.surname = surname;
            this.id = id;
            this.role = role;
            this.password = password;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder orders(int orders) {
            this.orders = orders;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }
        public Builder gender(String gender){
            this.gender=gender;
            return  this;
        }
        public Builder withCart(Cart cart){
            this.cart=cart;
            return this;
        }

        public Kupac build() {
            return new Kupac(this,cart);
        }
    }
}
