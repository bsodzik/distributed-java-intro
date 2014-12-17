package pl.edu.amu.dji.jms.lab11.review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.amu.dji.jms.lab11.review.model.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    Iterable<Review> findByIsbnAllIgnoreCase(String isbn);

}
