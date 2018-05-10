package cz.furyan.cardeditor;

public class EditorException extends RuntimeException {
    public EditorException(String message) {
        super(message);
    }

    public EditorException(String message, Throwable cause) {
        super(message, cause);
    }
}
