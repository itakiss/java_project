package hr.java.project.data;

import hr.java.project.entiteti.Change;
import hr.java.project.entiteti.Product;
import hr.java.project.ProjectApplication;
import hr.java.project.glavna.fxutil.ChangeWriter;
import hr.java.project.glavna.fxutil.Notification;
import hr.java.project.iznimke.ObjectExistsException;
import hr.java.project.threadovi.AddItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//LISTA ROBE U BAZI PODATAKA(OVO NAM TREBA)


public interface RobaData {

    String PRODUCTS_SERIALIZATION_FILE_NAME = "dat\\products.txt";

    static List<Product> getAllRoba() {
        List<Product> productList = new ArrayList<>();
        try (Connection conn = DataBase.connectingToDatabase()) {

            Statement sqlStatement = conn.createStatement();
            ResultSet robaResultSet = sqlStatement.executeQuery("SELECT * FROM PRODUCTS");

            while (robaResultSet.next()) {
                Product newProduct = getRoba(robaResultSet);
                productList.add(newProduct);
            }

        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
        return productList;
    }

    static Product getRoba(ResultSet procedureSet) throws SQLException {
        Integer id = procedureSet.getInt("ID");
        String naziv = procedureSet.getString("NAME");
        String boja = procedureSet.getString("COLOR");
        String spol = procedureSet.getString("GENDER");
        double cijena = procedureSet.getDouble("PRICE");
        String kategorija = procedureSet.getString("CATEGORY");
        String velicina = procedureSet.getString("SIZE");
        String description = procedureSet.getString("DESCRIPTION");

        Product productToAdd = new Product.Builder()
                .withId(id)
                .withName(naziv)
                .withColor(boja)
                .withGender(spol)
                .withPrice(cijena)
                .withCategory(kategorija)
                .withSize(velicina)
                .withDescription(description)
                .build();

        return productToAdd;
    }

    static void addRoba(String naziv, String category, Double price, String size, String color, String description, String gender) throws SQLException, IOException {
        try (Connection conn = DataBase.connectingToDatabase()) {
            String insertQuery = "INSERT INTO PRODUCTS(NAME, CATEGORY, PRICE, SIZE, COLOR, DESCRIPTION, GENDER) VALUES(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmnt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, naziv);
            stmnt.setString(2, category);
            stmnt.setDouble(3, price);
            stmnt.setString(4, size);
            stmnt.setString(5, color);
            stmnt.setString(6, description);
            stmnt.setString(7, gender);
            stmnt.executeUpdate();

            ResultSet generatedKeys = stmnt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                Product newProduct = new Product(generatedId, naziv, color, gender, price, category, size, description);

                List<Product> allProducts = getAllRoba();
                allProducts.add(newProduct);
                writeAllProduct(allProducts);

                Change change = new Change(new Product(0, "-", "-", "-", 0.0, "-", "-", "-"), newProduct);  // Set old object to null
                ChangeWriter changeWriter = new ChangeWriter(change);
                changeWriter.addChange(ProjectApplication.getLoggedUser().getRole().toString());

                ProjectApplication.executorService.execute(new AddItem(ProjectApplication.cart, newProduct, 1));

                Notification.addedSuccessfully("Stavka Robe");
            }
        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }


    static Product getRobaWithNaziv(String naziv) {
        Product newProduct = null;
        try (Connection conn = DataBase.connectingToDatabase()) {

            PreparedStatement sqlStatement = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE NAME = ?");
            sqlStatement.setString(1, naziv);
            ResultSet robaResultSet = sqlStatement.executeQuery();

            if (robaResultSet.next()) {
                newProduct = getRoba(robaResultSet);
            }

        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
        return newProduct;
    }

    static Product getProductFromId(Integer id) {
        Product newProduct = null;
        try (Connection conn = DataBase.connectingToDatabase()) {

            PreparedStatement sqlStatement = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE ID = ?");
            sqlStatement.setInt(1, id);
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                newProduct = getRoba(resultSet);
            }
        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
        return newProduct;
    }

    static void deleteProduct(Integer id) throws SQLException, IOException {
        try (Connection conn = DataBase.connectingToDatabase()) {
            Product productToRemove = RobaData.getProductFromId(id);

            PreparedStatement stmnt = conn.prepareStatement("DELETE FROM PRODUCTS WHERE ID = ?");
            stmnt.setInt(1, id);
            stmnt.executeUpdate();

            Change change = new Change(productToRemove, new Product(0, "-", "-", "-", 0.0, "-", "-", "-"));
            ChangeWriter changeWriter = new ChangeWriter(change);
            changeWriter.addChange(ProjectApplication.getLoggedUser().getRole().toString());

            writeAllProduct(getAllRoba());
            Notification.removedSuccessfully("Product");

        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    static void updateRoba(String newNaziv, String newCategory, Double newPrice, String newSize, String newColor, String newDescription, String newGender, Integer oldId) throws ObjectExistsException {
        Product oldProduct = RobaData.getProductFromId(oldId);
        try (Connection conn = DataBase.connectingToDatabase()) {
            String sql = "UPDATE PRODUCTS SET NAME = ?, CATEGORY = ?, PRICE = ?, SIZE = ?, COLOR = ?, DESCRIPTION = ?, GENDER = ? WHERE ID = ?";
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, newNaziv);
            stmnt.setString(2, newCategory);
            stmnt.setDouble(3, newPrice);
            stmnt.setString(4, newSize);
            stmnt.setString(5, newColor);
            stmnt.setString(6, newDescription);
            stmnt.setString(7, newGender);
            stmnt.setInt(8, oldId);
            stmnt.executeUpdate();

            Change change = new Change(oldProduct, new Product(oldId,newNaziv,newColor,newGender,newPrice,newCategory,newSize,newDescription));
            ChangeWriter changeWriter = new ChangeWriter(change);
            changeWriter.addChange(ProjectApplication.getLoggedUser().getRole());

            writeAllProduct(getAllRoba());
            Notification.updatedSuccessfully("Product");

        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    static void writeAllProduct(List<Product> products) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_SERIALIZATION_FILE_NAME));
            for (Product product : products) {
                writer.write(product.getId() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }
}
