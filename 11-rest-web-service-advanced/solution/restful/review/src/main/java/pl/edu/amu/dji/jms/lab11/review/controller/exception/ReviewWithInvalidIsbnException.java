package pl.edu.amu.dji.jms.lab11.review.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ReviewWithInvalidIsbnException extends RuntimeException {
    public ReviewWithInvalidIsbnException() {
        super("Review with invalid ISBN");
    }
}