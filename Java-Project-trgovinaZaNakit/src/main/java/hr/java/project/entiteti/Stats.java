package hr.java.project.entiteti;

public record Stats(Integer id, Integer roba, double cijena, Integer user, Integer bills) {

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public Integer roba() {
        return roba;}

    @Override
    public double cijena() {
        return cijena;
    }

    @Override
    public Integer user() {
        return user;
    }

    @Override
    public Integer bills() {
        return bills;
    }
}
