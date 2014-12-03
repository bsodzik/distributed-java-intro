package com.uam.exercise2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.uam.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class BookStoreExchangeIntegrationTest {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void shouldReturnListOfBooksAsJsonString() {
	}

	@Test
	public void shouldReturnListOfBooksAsJsonObject() {
	}

	@Test
	public void shouldReturnListOfBooksAsXmlString() {
	}

	@Test
	public void shouldReturnListOfBooksAsXmlObject() {
	}
}

