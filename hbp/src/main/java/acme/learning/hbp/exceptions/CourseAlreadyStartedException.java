package acme.learning.hbp.exceptions;

public class CourseAlreadyStartedException extends RuntimeException {
    public CourseAlreadyStartedException(String message) {
        super(message);
    }
}