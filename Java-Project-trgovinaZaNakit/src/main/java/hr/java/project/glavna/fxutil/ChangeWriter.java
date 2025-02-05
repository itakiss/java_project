package hr.java.project.glavna.fxutil;

import hr.java.project.entiteti.*;
import hr.java.project.ProjectApplication;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ChangeWriter<T>{

    private static final String CHANGE_FILE_ROBA = "dat\\changes\\products\\changesProducts.dat";
    private static final String CHANGE_FILE_TIME_ROBA = "dat\\changes\\products\\changesTimeProducts.txt";
    private static final String CHANGE_FILE_ROLE_ROBA = "dat\\changes\\products\\changesRoleProducts.txt";

    private static final String CHANGE_FILE_KORISNIK = "dat/changes/users/changesUsers.dat";
    private static final String CHANGE_FILE_TIME_KORISNIK= "dat\\changes\\users\\changesTimeUsers.txt";
    private static final String CHANGE_FILE_ROLE_KORISNIK = "dat\\changes\\users\\changesRoleUsers.txt";

    private static final String CHANGE_FILE_CART = "dat\\changes\\cart\\ChangesCart.dat";
    private static final String CHANGE_FILE_ROLE_CART = "dat\\changes\\cart\\changesRoleCart.txt";
    private static final String CHANGE_FILE_TIME_CART = "dat\\changes\\cart\\changesTimeCart.txt";




    private Change change;


    public ChangeWriter(Change change) {
        this.change = change;
    }
    public ChangeWriter(){}


    public void addChange(String role) {

        List items = null;

        if (change.getOldObject() instanceof Product) {
            items = readRoba();

        } else if (change.getOldObject() instanceof User) {
            items = readKorisnik();
        }


        items.add(change.getOldObject());
        items.add(change.getNewObject());

        writeAll(items, role);
    }

    public void writeAll(List<T> itemsToWrite, String role) {
        try{
            String first = null;
            String second = null;
            String third = null;

            T objectToCheck = itemsToWrite.get(0);
            if(objectToCheck instanceof Product){
                first = CHANGE_FILE_ROBA;
                second = CHANGE_FILE_TIME_ROBA;
                third = CHANGE_FILE_ROLE_ROBA;

            }else if(objectToCheck instanceof User){
                first = CHANGE_FILE_KORISNIK;
                second = CHANGE_FILE_TIME_KORISNIK;
                third = CHANGE_FILE_ROLE_KORISNIK;
            } else if(objectToCheck instanceof Cart){
                first = CHANGE_FILE_CART;
                second = CHANGE_FILE_TIME_CART;
                third = CHANGE_FILE_ROLE_CART;
            }

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(first, false));
            for(T object : itemsToWrite){
                out.writeObject(object);
            }
            out.close();
            out.flush();

            FileWriter timeWriter = new FileWriter(second, true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            timeWriter.write(dtf.format(now) + "\n");
            timeWriter.close();


            FileWriter roleWriter = new FileWriter(third, true);
            roleWriter.write(role + "\n");
            roleWriter.close();


        } catch (IOException e) {
            ProjectApplication.logger.info(e.getMessage(), e);
        }
    }

    public List<Product> readRoba() {
        List<Product> finalList = new ArrayList<>();
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(CHANGE_FILE_ROBA));
            while (true) {
                finalList.add((Product) input.readObject());
                finalList.add((Product) input.readObject());
            }
            } catch (IOException e) {
            ProjectApplication.logger.info(e.getMessage(), e);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        return finalList;
    }

    public List<User> readKorisnik(){
        List<User> finalList = new ArrayList<>();
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(CHANGE_FILE_KORISNIK));
            while (true) {
                finalList.add((User) input.readObject());
                finalList.add((User) input.readObject());
            }
            } catch (IOException e) {
            ProjectApplication.logger.info(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return finalList;
    }

    public List<String> readTimeRoba(){
        List<String> changesTime = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(CHANGE_FILE_TIME_ROBA))){
            while(scanner.hasNextLine()){
                String time = scanner.nextLine();
                changesTime.add(time);
            }
        } catch (FileNotFoundException e) {
            ProjectApplication.logger.info(e.getMessage(), e);
        }
        return changesTime;
    }


    public List<String> readTimeKorisnik(){
        List<String> changesTime = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(CHANGE_FILE_TIME_KORISNIK))){
            while(scanner.hasNextLine()){
                String time = scanner.nextLine();
                changesTime.add(time);
            }
        } catch (FileNotFoundException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
        return changesTime;
    }


    public List<String> readRoleChangeRoba(){
        List<String> changesRole = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(CHANGE_FILE_ROLE_ROBA))){
            while(scanner.hasNextLine()){
                String role = scanner.nextLine();
                changesRole.add(role);
            }
        } catch (FileNotFoundException e) {
            ProjectApplication.logger.info(e.getMessage(), e);
        }
        return changesRole;
    }

    public List<String> readRoleChangeKorisnik(){
        List<String> changesRole = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(CHANGE_FILE_ROLE_KORISNIK))){
            while(scanner.hasNextLine()){
                String scan = scanner.nextLine();
                changesRole.add(scan);
            }
        } catch (FileNotFoundException e) {
            ProjectApplication.logger.info(e.getMessage(), e);
        }
        return changesRole;
    }


    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }
}
