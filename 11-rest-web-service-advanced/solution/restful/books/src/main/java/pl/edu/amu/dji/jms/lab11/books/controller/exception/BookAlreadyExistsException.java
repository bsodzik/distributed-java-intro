package pl.edu.amu.dji.jms.lab11.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException() {
        super("Book already exists");
    }
}