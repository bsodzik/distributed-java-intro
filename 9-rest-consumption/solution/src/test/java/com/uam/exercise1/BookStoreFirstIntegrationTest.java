package com.uam.exercise1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
public class BookStoreFirstIntegrationTest {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void bookStoreShouldNotBeEmpty() {
		List<Book> result = restTemplate.getForObject(Book.URL, List.class);

		assertThat(result).isNotEmpty();
	}

	@Test
	public void bookStoreShouldNotBeEmptyAndElementsShouldBeConverted() {
		// Conversion does not work for generic collections - arrays have to be used
		Book[] result = restTemplate.getForObject(Book.URL, Book[].class);

		assertThat(result).isNotEmpty();
		assertThat(result).hasOnlyElementsOfType(Book.class);
	}
}
