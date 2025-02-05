package hr.java.project.entiteti;




import java.io.Serializable;


//Klasa koja opisuje robu, velicina cijena naziv sifra itd...


//Klasa koja opisuje robu, velicina cijena naziv sifra itd...


public class Product implements Serializable {
    private Integer id;
    private double price;
    private String naziv;
    private String boja;

    private String spol;
    private String velicina;
    private String kategorija;

    private String description;

    public String getVelicina() {
        return velicina;
    }

    public void setVelicina(String velicina) {
        this.velicina = velicina;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }

    public Product(Integer id, String naziv, String boja, String spol, double price, String kategorija, String velicina, String description) {
        this.id = id;
        this.naziv = naziv;
        this.boja = boja;
        this.spol = spol;
        this.price = price;
        this.kategorija = kategorija;
        this.velicina = velicina;
        this.description = description;
    }


    @Override
    public String toString() {

        return "Information about item: " + "\n" +
                "Id: " + id + "\n" +
                "Name: " + naziv + "\n" +
                "Category"+kategorija+"\n"+
                "Color: " + boja + "\n" +
                "Size"+velicina+"\n"+
                "Gender"+spol+"\n"+
                "Description: " + description+"\n"+
                "Price: " + price;

    }

    public static class Builder {
        private Integer id;
        private String naziv; // Name
        private String boja; // Color
        private String spol;
        private String velicina;
        private Double cijena; // Price
        private String kategorija;
        private String description;

        public Product build() {
            return new Product(id, naziv, boja, spol, cijena, kategorija, velicina, description);
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.naziv = name;
            return this;
        }

        public Builder withColor(String color) {
            this.boja = color;
            return this;
        }

        public Builder withGender(String gender) {
            this.spol = gender;
            return this;
        }

        public Builder withSize(String size) {
            this.velicina = size;
            return this;
        }

        public Builder withPrice(Double price) {
            this.cijena = price;
            return this;
        }

        public Builder withCategory(String category) {
            this.kategorija = category;
            return this;
        }



        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getBoja() {
        return boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
