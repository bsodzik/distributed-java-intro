package pl.edu.amu.dji.jms.lab11.review.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.amu.dji.jms.lab11.review.controller.exception.ReviewNotFoundException;
import pl.edu.amu.dji.jms.lab11.review.controller.exception.ReviewWithInvalidIsbnException;
import pl.edu.amu.dji.jms.lab11.review.model.Review;
import pl.edu.amu.dji.jms.lab11.review.repository.ReviewRepository;

@Controller
@RequestMapping("/reviews/{isbn}")
public class ReviewController {

    @Autowired
    private ReviewRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Review> find(@PathVariable String isbn){
        return repository.findByIsbnAllIgnoreCase(isbn);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Review getById(@PathVariable String isbn, @PathVariable Long id) {
        Review review = repository.findOne(id);
        if(review == null){
            throw new ReviewNotFoundException();
        }

        if(!review.getIsbn().equalsIgnoreCase(isbn)){
            throw new ReviewWithInvalidIsbnException();
        }

        return review;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Review addReview(@PathVariable String isbn, @RequestBody Review review) {
        if(!review.getIsbn().equalsIgnoreCase(isbn)){
            throw new ReviewWithInvalidIsbnException();
        }

        return repository.save(review);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Review updateBook(@PathVariable String isbn, @PathVariable Long id, @RequestBody Review review) {
        Review existingReview = repository.findOne(id);
        if(existingReview==null){
            throw new ReviewNotFoundException();
        }

        if(!review.getIsbn().equalsIgnoreCase(isbn)){
            throw new ReviewWithInvalidIsbnException();
        }

        existingReview.setIsbn(review.getIsbn());
        existingReview.setText(review.getText());
        existingReview.setTitle(review.getTitle());
        return repository.save(existingReview);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Review removeBook(@PathVariable String isbn, @PathVariable("id") Long id) {
        Review review = repository.findOne(id);
        if(review==null){
            throw new ReviewNotFoundException();
        }

        if(!review.getIsbn().equalsIgnoreCase(isbn)){
            throw new ReviewWithInvalidIsbnException();
        }

        repository.delete(id);

        return review;
    }
}
