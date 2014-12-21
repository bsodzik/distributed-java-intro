package com.uam.exercise2;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.uam.Application;
import com.uam.model.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class BookStoreExchangeIntegrationTest {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void shouldReturnListOfBooksAsJsonString() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<String> result = restTemplate.exchange(Book.URL, HttpMethod.GET, entity, String.class);

		System.out.println("Status: " + result.getStatusCode());
		System.out.println("Headers: " + result.getHeaders());
		System.out.println("Body: " + result.getBody());

		assertThat(result.getStatusCode()).isSameAs(HttpStatus.OK);
		assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
		assertThat(result.getBody()).isInstanceOf(String.class);
	}

	@Test
	public void shouldReturnListOfBooksAsJsonObject() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity entity = new HttpEntity(headers);

		ParameterizedTypeReference<List<Book>> responseType = new ParameterizedTypeReference<List<Book>>() {};
		ResponseEntity<List<Book>> result = restTemplate.exchange(Book.URL, HttpMethod.GET, entity, responseType);

		System.out.println("Status: " + result.getStatusCode());
		System.out.println("Headers: " + result.getHeaders());
		System.out.println("Body: " + result.getBody());

		assertThat(result.getStatusCode()).isSameAs(HttpStatus.OK);
		assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
		assertThat(result.getBody()).isInstanceOf(List.class);
		assertThat(result.getBody()).hasOnlyElementsOfType(Book.class);
	}

	@Test
	public void shouldReturnListOfBooksAsXmlString() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/xml");
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<String> result = restTemplate.exchange(Book.URL, HttpMethod.GET, entity, String.class);

		System.out.println("Status: " + result.getStatusCode());
		System.out.println("Headers: " + result.getHeaders());
		System.out.println("Body: " + result.getBody());

		assertThat(result.getStatusCode()).isSameAs(HttpStatus.OK);
		assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_XML);
		assertThat(result.getBody()).isInstanceOf(String.class);
	}

	@Test
	public void shouldReturnListOfBooksAsXmlObject() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/xml");
		HttpEntity entity = new HttpEntity(headers);

		ParameterizedTypeReference<List<Book>> responseType = new ParameterizedTypeReference<List<Book>>() {};
		ResponseEntity<List<Book>> result = restTemplate.exchange(Book.URL, HttpMethod.GET, entity, responseType);

		System.out.println("Status: " + result.getStatusCode());
		System.out.println("Headers: " + result.getHeaders());
		System.out.println("Body: " + result.getBody());

		assertThat(result.getStatusCode()).isSameAs(HttpStatus.OK);
		assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_XML);
		assertThat(result.getBody()).isInstanceOf(List.class);
		assertThat(result.getBody()).hasOnlyElementsOfType(Book.class);
	}
}
