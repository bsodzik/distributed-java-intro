package pl.edu.amu.dji.jms.lab11.books.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.amu.dji.jms.lab11.books.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {

    List<Book> findByTitleLikeAllIgnoreCase(String title);

    List<Book> findByAuthorsLikeAllIgnoreCase(String author);

    List<Book> findByTitleLikeAndAuthorsLikeAllIgnoreCase(String title, String author);
}
