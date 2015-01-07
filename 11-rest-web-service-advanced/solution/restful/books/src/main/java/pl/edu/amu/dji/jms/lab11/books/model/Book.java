package pl.edu.amu.dji.jms.lab11.books.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {

    //http://docs.spring.io/spring/docs/4.1.0.BUILD-SNAPSHOT/spring-framework-reference/htmlsingle/#mvc-ann-jsonview
    public interface MinimalView {};

    @Id
    @JsonView(MinimalView.class)
    private String isbn;

    @NotBlank
    @JsonView(MinimalView.class)
    private String title;

    @NotBlank
    @JsonView(MinimalView.class)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="AUTHORS", joinColumns=@JoinColumn(name="BOOK_ID"))
    @Column(name="AUTHOR")
    @JsonView(MinimalView.class)
    private List<String> authors;

    @Transient
    private Iterable<Review> reviews;

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

    public Iterable<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Iterable<Review> reviews) {
        this.reviews = reviews;
    }
}
