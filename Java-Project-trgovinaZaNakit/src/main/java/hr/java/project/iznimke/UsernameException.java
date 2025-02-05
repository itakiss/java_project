package hr.java.project.iznimke;

public class UsernameException extends Exception {
    public UsernameException() {
    }

    public UsernameException(String message) {
        super(message);
    }

    public UsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameException(Throwable cause) {
        super(cause);
    }
}
