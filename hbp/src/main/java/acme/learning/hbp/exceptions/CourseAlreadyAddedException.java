package acme.learning.hbp.exceptions;

public class CourseAlreadyAddedException extends RuntimeException {
    public CourseAlreadyAddedException(String message) {
        super(message);
    }
}
