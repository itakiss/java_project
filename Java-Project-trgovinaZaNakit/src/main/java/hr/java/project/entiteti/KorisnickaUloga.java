package hr.java.project.entiteti;

import java.io.Serializable;
import java.util.Arrays;
//ovo je enum to bi trebalo biti okej
public enum KorisnickaUloga implements Serializable {
    ADMIN("ADMIN"),
    KUPAC("KUPAC");

    private final String name;

    KorisnickaUloga(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static KorisnickaUloga parse(String string) {
        return Arrays.stream(values())
                .filter(t -> t.getName().equals(string))
                .findFirst()
                .orElse(KUPAC);
    }

    @Override
    public String toString() {
        return name;
    }
}
