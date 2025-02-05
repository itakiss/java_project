package hr.java.project.data;


import hr.java.project.entiteti.Change;
import hr.java.project.ProjectApplication;
import hr.java.project.glavna.fxutil.ChangeWriter;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.java.project.entiteti.Cart;
import hr.java.project.entiteti.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;

public interface CartData {

    String CART_SERIALIZATION_FILE_NAME = "dat/cart.txt";

    static List<Product> getAllCart() {
        List<Product> productList = new ArrayList<>();
        try (Connection conn = DataBase.connectingToDatabase()) {
            Statement sqlStatement = conn.createStatement();
            ResultSet cartResultSet = sqlStatement.executeQuery("SELECT * FROM CART_ITEM CI INNER JOIN PRODUCTS P ON CI.PRODUCT_ID = P.ID");

            while (cartResultSet.next()) {
                Product newProduct = getProductFromResultSet(cartResultSet);
                productList.add(newProduct);
            }
        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
        return productList;
    }

    static Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("ID");
        String name = resultSet.getString("NAME");
        String category = resultSet.getString("CATEGORY");
        String size = resultSet.getString("SIZE");
        String color = resultSet.getString("COLOR");
        String description = resultSet.getString("DESCRIPTION");
        String gender = resultSet.getString("GENDER");
        double price = resultSet.getDouble("PRICE");

        return new Product(id, name, color, gender, price, category, size, description);
    }

    static boolean saveToCart(Product product, String userId) {
        try (Connection conn = DataBase.connectingToDatabase()) {
            // Insert the product into the CART table
            PreparedStatement cartStmnt = conn.prepareStatement("INSERT INTO CART(ID) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            cartStmnt.setString(1, userId);
            cartStmnt.executeUpdate();

            ResultSet cartKeys = cartStmnt.getGeneratedKeys();
            int cartId;
            if (cartKeys.next()) {
                cartId = cartKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve the generated cart ID.");
            }

            // Insert the product into the CART_ITEM table
            PreparedStatement itemStmnt = conn.prepareStatement("INSERT INTO CART_PRODUCTS(CART_ID, PRODUCT_ID) VALUES (?, ?)");
            itemStmnt.setInt(1, cartId);
            itemStmnt.setInt(2, product.getId());
            itemStmnt.executeUpdate();

            return true;
        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
            return false;
        }
    }

    static List<Product> getCartsByUserId(String userId) {
        List<Product> productList = new ArrayList<>();
        try (Connection conn = DataBase.connectingToDatabase()) {
            PreparedStatement sqlStatement = conn.prepareStatement("SELECT * FROM CART_PRODUCTS CI INNER JOIN PRODUCTS P ON CI.PRODUCT_ID = P.ID WHERE CI.CART_ID IN (SELECT CART_ID FROM CART WHERE ID = ?)");
            sqlStatement.setString(1, userId);
            ResultSet cartResultSet = sqlStatement.executeQuery();

            while (cartResultSet.next()) {
                Product newProduct = getProductFromResultSet(cartResultSet);
                productList.add(newProduct);
            }
        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
        return productList;
    }

    static boolean deleteProduct(Product product) throws SQLException, IOException {
        try (Connection conn = DataBase.connectingToDatabase()) {
            PreparedStatement stmnt = conn.prepareStatement("DELETE FROM CART_PRODUCTS WHERE PRODUCT_ID = ? AND CART_ID IN (SELECT CART_ID FROM CART WHERE ID = ?)");
            stmnt.setInt(1, product.getId());
            stmnt.setString(2, ProjectApplication.getLoggedUser().getId());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);return false;
        }

    }
    static boolean deleteCart(String userId) {
        try (Connection conn = DataBase.connectingToDatabase()) {
            // Delete the cart items
            PreparedStatement itemStmnt = conn.prepareStatement("DELETE FROM CART_PRODUCTS WHERE CART_ID IN (SELECT CART_ID FROM CART WHERE ID = ?)");
            itemStmnt.setString(1, userId);
            itemStmnt.executeUpdate();

            // Delete the cart
            PreparedStatement cartStmnt = conn.prepareStatement("DELETE FROM CART WHERE ID = ?");
            cartStmnt.setString(1, userId);
            cartStmnt.executeUpdate();

            return true;
        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
            return false;
        }
    }


    static void writeAllCart(List<Product> cart) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CART_SERIALIZATION_FILE_NAME));
            for (Product carts : cart) {
                writer.write(carts.getId() + " " + ProjectApplication.getLoggedUser().getId() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }
}
