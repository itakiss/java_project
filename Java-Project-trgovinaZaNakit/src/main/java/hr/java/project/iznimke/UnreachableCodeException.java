package hr.java.project.iznimke;

public class UnreachableCodeException extends RuntimeException {
    public UnreachableCodeException() {
    }

    public UnreachableCodeException(String message) {
        super(message);
    }

    public UnreachableCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnreachableCodeException(Throwable cause) {
        super(cause);
    }
}
