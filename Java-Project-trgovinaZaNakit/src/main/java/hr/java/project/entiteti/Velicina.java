package hr.java.project.entiteti;

import java.io.Serializable;
//velicina nije iskoristena
public enum Velicina implements Serializable {

    XS(34,"Extra small"),
    S(36,"Small"),
    M(38,"Medium"),
    L(40,"Large"),
    XL(42,"Extra Large"),
    ONE_SIZE(0,"Jedna Veličina");
    private final Integer velicina;
    private final String opis;


    Velicina(Integer velicina,String opis ) {
        this.opis = opis;
        this.velicina = velicina;
    }

    public Integer getVelicina() {
        return velicina;
    }

    public String getOpis() {
        return opis;
    }
    @Override
    public String toString() {
        return opis;
    }
}
