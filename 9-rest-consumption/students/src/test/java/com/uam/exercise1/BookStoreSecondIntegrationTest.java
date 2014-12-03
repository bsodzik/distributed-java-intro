package com.uam.exercise1;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.uam.Application;
import com.uam.model.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class BookStoreSecondIntegrationTest {

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
	public void bookShouldBeCreatedAndDeleted() {

	}
}
