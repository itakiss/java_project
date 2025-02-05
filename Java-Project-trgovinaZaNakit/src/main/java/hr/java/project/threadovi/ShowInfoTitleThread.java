package hr.java.project.threadovi;

import hr.java.project.entiteti.Cart;
import hr.java.project.ProjectApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowInfoTitleThread implements Runnable{



    public ShowInfoTitleThread() {

    }

    @Override
    public void run() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String now = formatter.format(date);


        ProjectApplication.getStage().setTitle("Marija Butik                                                         " + now + "                                                                                                                  ---- Nova ljetna kolekcija!");
    }
}