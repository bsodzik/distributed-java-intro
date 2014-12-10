## Labs 10 - Book RESTful Web Service ##

### Goal ###
- Get familiar with *Spring Boot*
- Get familiar with *RESTful Web* Services in Spring.
- Implement *Book* RESTful Web Service
- Implement exception handling for *Book* RESTful Web Service
- Implement ETag for *Book* RESTful Web Service

### Introduction ###

**Get familiar with [Spring Boot](http://projects.spring.io/spring-boot/)**
In general Spring Boot is:
- Ready to use spring based [project template](http://start.spring.io/)
	- All required dependencies are in
    - With initial project starter [Spring Java Based Configuration](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#beans-java)
        - No XML configuration required
- Standalone java application (because contains embedded Tomcat)

**Spring Boot Quick Start**
- Read official [Quick Start](http://projects.spring.io/spring-boot/#quick-start)
- Identify
    - Required maven dependencies
    - Java Based Configuration
    - Controller
    - How to start application
- Ask as many questions as you want
- Questions
    - Is this 'out of the box' approach is good?
    - Do you see any risk in this approach?

**Spring RESTful Web Service quick start**
- Remind [rest basics](https://github.com/bsodzik/distributed-java-intro/tree/master/8-rest-basics), especially theory from *Exercise 2*
- You can allays read more about [REST](http://spring.io/understanding/REST) if required
- After reading you need to:
    - Understand concept of resources
    - Know and understand all HTTP methods (GET, POST, PUT, DELETE)
    - Know which "Media type" is related to JSON
    - Have general understanding of status codes
- Read official [Building a RESTful Web Service](http://spring.io/guides/gs/rest-service/) getting started
    - Remember that we are working with maven (you can omit Gradle and STS part)
    - Feel free to start this tutorial on lab and ask as many questions as you want
    - Identify:
        - How to define model (Greeting)?
        - How to define resource controller (GreetingController)?
        - How to map URI to the controller method?
- You can always check Spring MVC [docs](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc)

**Spring RESTful Web Service example:**

- GET /users - all users: 

		@RestController
		@RequestMapping(value="/users")
		public class UserController {
			@RequestMapping(method = RequestMethod.GET)
			public Iterable<User> users(){...}
			...
		}

- GET /users/1337 - get user identified by 1337 id: 

		@RequestMapping(value="/{id}", method = RequestMethod.GET)
		public User getUser(@PathVariable Long id){...}

- PUT /users/1337 - update user identified by 1337 id: 
		
		@RequestMapping(value="/{id}", method = RequestMethod.PUT)
		public User updateUser(@PathVariable Long id, @RequestBody User user){...}

- Used annotations:
    - [@RestController](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-restcontrolle)
    - [@RequestMapping](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping)
    - [@PathVariable](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping-uri-templates)
    - [@RequestParam](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-requestparam)

- Media types
	- [Producible Media Types](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping-produces)
	- [Consumable Media Types](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping-consumes)

### Realization ###

**Exercise 1: Implement RESTful Book Web Service**
- RESTful Book Web Service should produces and consumes "application/json" and provide functionality listed below:
	- Retrieve list of books:

	    curl -H 'Accept: application/json' -X GET -D - http://localhost:8080/books

	- Create new book:

        curl -H 'Accept: application/json' -H 'Content-Type: application/json' -X POST -d '{"isbn":"1234567890","title":"Be creative","description":"Book for people who are more than copy and paste..","authors":["Captain Obvious", "Tony Montana"]}' -D - http://localhost:8080/books

	- Return book for given id (path variable):

	    curl -H 'Accept: application/json' -X GET -D - http://localhost:8080/books/1234567890

	- Update book for given id (path variable) and with given json request body:

	    curl -H 'Accept: application/json' -H 'Content-Type: application/json' -X PUT -d '{"isbn":"1234567890","title":"Be creative 2","description":"Book for people who are more than copy and paste..","authors":["Captain Obvious", "Tony Montana"]}' -D - http://localhost:8080/books/1234567890

	- Remove book for given id (path variable):

	    curl -H 'Accept: application/json' -H 'Content-Type: application/json' -X DELETE -D - http://localhost:8080/books/1234567890

Implementation hints:
- For DB operation RESTful Book Web Service should use BookRepository, you can inject it by @Autowired annotation
- Book class is mapped to BOOK database table by [hibernate](http://hibernate.org/)
- Application is using H2 in memory database
- To access H2 web console go to [http://localhost:8080/console](http://localhost:8080/console). JDBC URL, user name and password are in /application.properties file (in resources folder)
- To start application run (or debug) pl.edu.amu.dji.jms.lab10.books.BookConfiguration in IDE.
- [Spring Java Based Configuration](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#beans-java) is in BookConfiguration class.
	 
**Exercise 2: Implement exception handling in Book REST service**
Check how to define status codes for [business exceptions](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-annotated-exceptions). Below example:

		@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Order")
    	public class OrderNotFoundException extends RuntimeException {...}

- BookAlreadyExistsException should be related with CONFLICT status
- BookNotFoundException should be related with NOT_FOUND status
- BookController should throw
	- BookNotFoundException when user wants to GET, PUT or DELETE non-existent book.
	- BookAlreadyExistsException when user wants to POST existing book.

**Exercise 3: ETag support**
- Read about [ETag spring support](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-etag)
- In BookConfiguration declare @Bean of type Filter which return new instance of ShallowEtagHeaderFilter
- Test it in the same way as in [8-rest-basics](https://github.com/bsodzik/distributed-java-intro/tree/master/8-rest-basics)
