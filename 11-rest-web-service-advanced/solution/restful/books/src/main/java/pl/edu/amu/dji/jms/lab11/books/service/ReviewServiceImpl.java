package pl.edu.amu.dji.jms.lab11.books.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.amu.dji.jms.lab11.books.model.Review;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable("reviews")
    @Override
    public Iterable<Review> getReviews(String isbn) {
        ParameterizedTypeReference<Iterable<Review>> responseType = new ParameterizedTypeReference<Iterable<Review>>() {};

        ResponseEntity<Iterable<Review>> entity = restTemplate.exchange("http://localhost:8090/reviews/{isbn}", HttpMethod.GET, null, responseType, isbn);
        Preconditions.checkState(entity.getStatusCode() == HttpStatus.OK);
        return entity.getBody();
    }
}
