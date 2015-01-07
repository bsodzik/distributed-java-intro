package pl.edu.amu.dji.jms.lab10.books.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String isbn;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="AUTHORS", joinColumns=@JoinColumn(name="BOOK_ID"))
    @Column(name="AUTHOR")
    private List<String> authors;

    public Book() {
    }

    public Book(String isbn, String title, String description, List<String> authors) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}
