package pl.edu.amu.dji.jms.lab10.books.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.amu.dji.jms.lab10.books.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
