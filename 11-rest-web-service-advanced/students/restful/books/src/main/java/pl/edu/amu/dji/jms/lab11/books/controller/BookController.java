package pl.edu.amu.dji.jms.lab11.books.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.amu.dji.jms.lab11.books.controller.exception.BookAlreadyExistsException;
import pl.edu.amu.dji.jms.lab11.books.controller.exception.BookNotFoundException;
import pl.edu.amu.dji.jms.lab11.books.model.Book;
import pl.edu.amu.dji.jms.lab11.books.repository.BookRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(Book.MinimalView.class)
    public Iterable<Book> books() {
        return repository.findAll();
    }


    @RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
    public Book getByIsbn(@PathVariable String isbn) {
        Book book = repository.findOne(isbn);
        if (book == null) {
            throw new BookNotFoundException();
        }

        return book;
    }

    @RequestMapping(method = RequestMethod.POST)
    @JsonView(Book.MinimalView.class)
    public Book addBook(@Valid @RequestBody Book book) {
        if (repository.exists(book.getIsbn())) {
            throw new BookAlreadyExistsException();
        }

        return repository.save(book);
    }

    @RequestMapping(value = "/{isbn}", method = RequestMethod.PUT)
    @JsonView(Book.MinimalView.class)
    public Book updateBook(@PathVariable String isbn, @Valid @RequestBody Book book) {
        Book existingBook = repository.findOne(isbn);
        if (existingBook == null) {
            throw new BookNotFoundException();
        }

        existingBook.setTitle(book.getTitle());
        existingBook.setDescription(book.getDescription());
        existingBook.setAuthors(book.getAuthors());

        return repository.save(existingBook);
    }

    @RequestMapping(value = "/{isbn}", method = RequestMethod.DELETE)
    @JsonView(Book.MinimalView.class)
    public Book removeBook(@PathVariable String isbn) {
        Book book = repository.findOne(isbn);
        if (book == null) {
            throw new BookNotFoundException();
        }

        repository.delete(isbn);
        return book;
    }
}
