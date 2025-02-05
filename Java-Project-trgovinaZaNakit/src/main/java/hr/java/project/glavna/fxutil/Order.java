package hr.java.project.glavna.fxutil;

import hr.java.project.entiteti.Product;
import hr.java.project.entiteti.User;
import hr.java.project.glavna.api.EmailUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record Order(String orderId, User user, List<Product> products, LocalDate orderDate, double totalAmount) {
    // Additional methods or fields specific to the order

    public void printOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("User: " + user);
        System.out.println("Products: " + products);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Total Amount: $" + totalAmount);
    }

    public void writeToTextFile(String fileName) {
        // Prepare the file content
        StringBuilder fileContent = new StringBuilder();
        fileContent.append("--------------------------------------------------\n");
        fileContent.append("               Purchase Bill\n");
        fileContent.append("--------------------------------------------------\n\n");
        fileContent.append("Buyer: ").append(user.getName()).append("\n");
        fileContent.append("Order ID: ").append(orderId).append("\n");
        fileContent.append("Order Date: ").append(orderDate).append("\n\n");
        fileContent.append("Products:\n");
        for (Product product : products) {
            fileContent.append("- ").append(product.getNaziv()).append(" (").append(product.getPrice()).append(" EUR)\n");
        }
        fileContent.append("\nTotal Price: ").append(totalAmount).append(" EUR\n\n");
        fileContent.append("--------------------------------------------------\n");
        fileContent.append("        Thank you for your purchase!\n");
        fileContent.append("--------------------------------------------------\n");

        // Write the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(fileContent.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public void sendConfirmationEmail(String fileName) {
        String subject = "Order Confirmation";
        String body = "Dear " + user.getName() + ",\n\n"
                + "Thank you for your recent purchase from our online store! We appreciate your support and trust in our products.\n\n"
                + "Here are the details of your order:\n\n"
                + "-------------------------------------------\n"
                + "Order Summary:\n"
                + "-------------------------------------------\n";

        for (Product product : products) {
            body += "Product: " + product.getNaziv() + "\n";
            body += "Price: " + product.getPrice() + " EUR\n";
            body += "-------------------------------------------\n";
        }

        body += "Total Price: " + totalAmount + " EUR\n\n"
                + "If you have any questions or need further assistance, please feel free to contact our customer support team.\n\n"
                + "Thank you again for choosing our store!\n\n"
                + "Best regards,\n"
                + "Your Store Name";

        // Send the email using your email utility class or API
        EmailUtil.sendEmail(subject, body,fileName);
    }

}