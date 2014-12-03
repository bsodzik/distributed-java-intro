## Labs 9 - REST - consumption ##


### Goal ###
- Get familiar with Spring RestTemplate
- Learn how to use higher level, convenient methods
- Learn how to use low level, verbose method

### Realization ###

RestTemplate (see [docs](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)) is Spring's central class for synchronous client-side HTTP access. It simplifies communication with HTTP servers, and enforces RESTful principles. It handles HTTP connections, leaving application code to provide URLs (with possible template variables) and extract results. 


Java objects can be automatically converted to text representation that can be then sent as HTTP request body. Accordingly, HTTP response body can be automatically converted to Java object. RestTemplate provides several `HttpMessageConverter` implementations. Two of them are

- `MappingJackson2HttpMessageConverter` - converts Java ⇆ JSON
- `Jaxb2RootElementHttpMessageConverter` - converts Java ⇆ XML

**Exercise 1**

RestTemplate provides higher level methods corresponding to HTTP methods. These methods simplifies basic REST calls.

- `getForObject` - Invokes provided `url` using GET method and converts result to instance of `responseType` class. The `url` may contain variables which should be surrounded with curly braces, e.g. *http://localhost/users/{userId}*. Values for such variables can be passed in `urlVariables` parameter, either as `varargs` or `Map`. If status code of invoked operation is different than *200* then `RestClientException` is thrown. Exception contains HTTP response status code, headers and body.
 
		public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException;

		public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException;

- `postForObject` - Invokes provided `url` using POST method. Marshals `request` object to text representation and unmarshals result to instance of `responseType` class.

		public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException;

		public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;

- `put` - Invokes provided `url` using PUT method. Marshals `request` object to text representation.

		public void put(String url, Object request, Object... urlVariables) throws RestClientException;

		public void put(String url, Object request, Map<String, ?> urlVariables) throws RestClientException;

- `delete` - Invokes provided `url` using DELETE method.

		public void delete(String url, Object... urlVariables) throws RestClientException;

		public void delete(String url, Map<String, ?> urlVariables) throws RestClientException;

The goal of this exercise is to implement several integration tests to [Online Book Store](http://bsodzik.herokuapp.com/books) known from previous labs.

1. Create test checking if there are any books in the store
	- Implement test class `BookStoreFirstIntegrationTest`
	- Retrieve list of books (`List<Book>`) using `getForObject` method. Use `List.class` as a `responseType` parameter
	- Use [AssertJ](http://joel-costigliola.github.io/assertj/) *fluent API* to verify that list of books is not empty. Start your assertion with `Assertions.assertThat` method followed by appropriate checking method 


2. Create test that adds a book and then deletes it
	- Implement test class `BookStoreSecondIntegrationTest`
	- Create new instance of a book in `setUp` method. Remember to provide unique ISBN number to avoid collisions with other students
	- Use `getForObject` method to ensure there is no book with given ISBN number already. Create URL by combining base URL with book ISBN number. Catch `HttpClientErrorException` and assert that status code of exception is `HttpStatus.NOT_FOUND`
	- Use `postForObject` method to add book to the Book Store. Assert that book returned from `postForObject` method is equals to the book passed as parameter to that method
	- Use `getForObject` method once again. This time book should be retrieved from the Book Store
	- Use `delete` method to delete book from the Book Store
	- Use `getForObject` method again. This time book should not be available in the Book Store. Write proper assertions to verify that


3. Create test that adds the same book two times
	- Implement test class `BookStoreThirdIntegrationTest`
	- Create new instance of a book in `setUp` method. Remember to provide unique ISBN number to avoid collisions with other students
	- Use `getForObject` method to ensure there is no book with given ISBN number already
	- Use `postForObject` method to add book. Verify that book was added using proper assertion
	- Use `postForObject` method to add book once more. Exception should be thrown with status `409 Conflict`
	- Delete created book at the end of the test

**Exercise 2**

The drawback of higher level methods is inability to customize HTTP request headers. For example, HTTP request header `Accept` is set to `application/json, application/*+json` what means that XML based services are not supported.

In addition to higher level methods RestTemplate provides also low level `exchange` method. That method allows more precise customization of HTTP request. There are several overloaded versions of `exchange` method but two most convenient are

	public <T> ResponseEntity<T> exchange(String url, HttpMethod method,
			HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {

	public <T> ResponseEntity<T> exchange(String url, HttpMethod method,
			HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {

`ResponseEntity<T>` class represents HTTP response. It contains following methods

- `getStatusCode` - returns status code of HTTP response
- `getBody` - returns HTTP response body converted to Java object (instance of type `T`)
- `getHeaders` - returns HTTP response headers

`HttpEntity` class represents HTTP request body and headers. This class is immutable and both body and headers can be assigned only during object construction (through constructor parameters). Creation of `HttpEntity` without HTTP body should look as follows

	HttpHeaders headers = new HttpHeaders();
	headers.set("Accept", "application/xml");

	HttpEntity entity = new HttpEntity(headers);

`HttpHeaders` class represents headers of either HTTP request or HTTP response. It contains convenient methods for setting most popular headers.


The goal of this exercise is to retrieve books in both XML and JSON format. All scenarios should be implemented in `BookStoreExchangeIntegrationTest` class using RestTemplate `exchange` method.

1. Implement test `shouldReturnListOfBooksAsJsonString`
	- Create `HttpHeaders` object and add `Accept` header with value `application/json`
	- Create `HttpEntity` object based on created headers
	- Use `exchange` method to retrieve all books as JSON. Use `String` class as `responseType`. Assign value returned from `exchange` method to variable of type `ResponseEntity<String>`
	- Display status code, headers and body of response entity

2. Implement test `shouldReturnListOfBooksAsJsonObject`
	- Create `HttpHeaders` object and add `Accept` header with value `application/json`
	- Create `HttpEntity` object based on created headers
	- Create `responseType` variable of type `ParameterizedTypeReference<List<Book>>`. Use following code snippet for that
			
			ParameterizedTypeReference<List<Book>> responseType = new ParameterizedTypeReference<List<Book>>() {};

	- Use `exchange` method to retrieve all books as JSON. Use created `responseType` variable. Assign value returned from `exchange` method to variable of type `ResponseEntity<List<Book>>`
	- Display status code, headers and body of response entity

3. Implement test `shouldReturnListOfBooksAsXmlString`
	- Create `HttpHeaders` object and add `Accept` header with value `application/xml`
	- Create `HttpEntity` object based on created headers
	- Use `exchange` method to retrieve all books as XML. Use `String` class as `responseType`. Assign value returned from `exchange` method to variable of type `ResponseEntity<String>`
	- Display status code, headers and body of response entity

4. Implement test `shouldReturnListOfBooksAsXmlObject`
	- Create `HttpHeaders` object and add `Accept` header with value `application/xml`
	- Create `HttpEntity` object based on created headers
	- Create `responseType` variable of type `ParameterizedTypeReference<List<Book>>`. Use following code snippet for that
			
			ParameterizedTypeReference<List<Book>> responseType = new ParameterizedTypeReference<List<Book>>() {};

	- Use `exchange` method to retrieve all books as XML. Use created `responseType` variable. Assign value returned from `exchange` method to variable of type `ResponseEntity<List<Book>>`
	- Display status code, headers and body of response entity

