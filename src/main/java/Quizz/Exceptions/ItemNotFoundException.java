package Quizz.Exceptions;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String fieldName) {
        super(fieldName);
    }
}
