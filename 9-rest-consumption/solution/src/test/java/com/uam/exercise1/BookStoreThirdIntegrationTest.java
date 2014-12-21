package com.uam.exercise1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.uam.Application;
import com.uam.model.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class BookStoreThirdIntegrationTest {

	@Autowired
	private RestTemplate restTemplate;

	private String uniqueIsbn;
	private Book testBook;

	@Before
	public void setUp() {
		uniqueIsbn = Long.toString(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE / 100, Long.MAX_VALUE));
		testBook = new Book(uniqueIsbn, "testTitle", "testDescription", "testAuthor");
	}

	@Test
	public void bookShouldNotBeAddedTwice() {

		// Make sure book for given ISBN is not available
		verifyThatBookIsNotAvailable(uniqueIsbn);

		// Add book to the library
		Book actualBook = restTemplate.postForObject(Book.URL, testBook, Book.class);
		assertThat(actualBook).isEqualTo(testBook);

		// Try to add the same book once more
		try {
			restTemplate.postForObject(Book.URL, testBook, Book.class);
			fail("Should never reach that code - Exception should be thrown");
		} catch (HttpClientErrorException ex) {
			assertThat(ex.getStatusCode()).isSameAs(HttpStatus.CONFLICT);
		}

		// Delete added book
		restTemplate.delete(Book.URL + uniqueIsbn);

		// Make sure book for given ISBN is not available anymore
		verifyThatBookIsNotAvailable(uniqueIsbn);
	}

	private void verifyThatBookIsNotAvailable(String uniqueIsbn) {
		try {
			restTemplate.getForObject(Book.URL + uniqueIsbn, Book.class);
			fail("Should never reach that code - Exception should be thrown");
		} catch (HttpClientErrorException ex) {
			assertThat(ex.getStatusCode()).isSameAs(HttpStatus.NOT_FOUND);
		}
	}
}
