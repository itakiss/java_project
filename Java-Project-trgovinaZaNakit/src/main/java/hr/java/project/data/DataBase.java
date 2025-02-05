package hr.java.project.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.sql.*;

//SPAJANJE NA BAZU PODATAKA(OVO NAM TREBA)
public interface DataBase{
    static final Logger logger = LoggerFactory.getLogger(DataBase.class);
    static String DATABASE_FILE = "database.properties";

    static String COUNTRIES = "dat\\countries.txt";

    public static Connection connectingToDatabase() throws IOException, SQLException {
        Connection conn;
        try{
            Properties properties = new Properties();
            properties.load(new FileReader(DATABASE_FILE));
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("pass");
            conn = DriverManager.getConnection(url, user,pass);

        }catch (IOException e){
            throw new IOException("Error while reading properties file for DB.", e);
        }catch (SQLException e){
            throw new SQLException("Error while connecting to database.", e);
        }
        return conn;
    }

   /* static Request connectingToApi(String url) throws IOException {
        try{
            Properties properties = new Properties();
            properties.load(new FileReader(API_FILE));
            String key = properties.getProperty("X-RapidAPI-Key");
            String host = properties.getProperty("X-RapidAPI-Host");

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("X-RapidAPI-Key", key)
                    .addHeader("X-RapidAPI-Host", host)
                    .build();

            return request;

        }catch (IOException e){
            throw new IOException("Error while reading properties file for API call.", e);
        }
    }

    **/


}

