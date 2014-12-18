## Labs 11 - JsonView, Cacheable and RestTemplate in Book RESTful Web Service ##

### Goal ###
- Implement *Review RESTful web service*
- Retrieve reviews for given book in *Book RESTful Web Service*
- Get familiar with *Spring Cache Abstraction*
- Get familiar with *Jackson Serialization View Support*

### Realization ###

**Mandatory Exercise 1: Implement Review REST service**

- Refresh knowledge about: [Spring RESTful Web Services](https://github.com/bsodzik/distributed-java-intro/tree/master/10-rest-web-service)
- RESTful Review Web Service should produces and consumes "application/json" and provide functionality listed below:
	- Retrieve list of reviews for given ISBN */reviews/{isbn}*: 

			curl -H 'Accept: application/json' -X GET -D - http://localhost:8090/reviews/1234567890

	- Create new review for given ISBN  */reviews/{isbn}* and throw ReviewWithInvalidIsbnException when isbn from path is not the same as in request body
	
			curl -H 'Accept: application/json' -H 'Content-Type: application/json' -X POST -d '{"isbn":"1234567890","title":"Review title","text":"Review text"}' -D - http://localhost:8090/reviews/1234567890



	- Return review for given ISBN and id  */reviews/{isbn}/{id}*, throw ReviewWithInvalidIsbnException when isbn from path is not the same as in request body, throw ReviewNotFoundException if there is no review for given ID

			curl -H 'Accept: application/json' -X GET -D - http://localhost:8090/books/reviews/1234567890/1co

	- Update review for given ISBN and id  */reviews/{isbn}/{id}*, throw ReviewWithInvalidIsbnException when isbn from path is not the same as in request body, throw ReviewNotFoundException if there is no review for given ID

			curl -H 'Accept: application/json' -H 'Content-Type: application/json' -X PUT -d '{"isbn":"1234567890","title":"Review title2","text":"Review text2"}' -D - http://localhost:8090/reviews/1234567890/1

	- Remove review for given ISBN and id */reviews/{isbn}/{id}*, throw ReviewWithInvalidIsbnException when isbn from path is not the same as in request body, throw ReviewNotFoundException if there is no review for given ID

			curl -H 'Accept: application/json' -H 'Content-Type: application/json' -X DELETE -D - http://localhost:8090/reviews/1234567890/1

Implementation hints:
- For DB operation RESTful Review Web Service should use ReviewRepository, you can inject it by @Autowired annotation
- Review class is mapped to REVIEW database table by [hibernate](http://hibernate.org/)
- Application is using H2 in memory database
- To access H2 web console go to [http://localhost:8090/console](http://localhost:8090/console). JDBC URL, user name and password are in /application.properties file (in resources folder)
- To start application run (or debug) ReviewConfiguration in IDE.
- [Spring Java Based Configuration](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#beans-java) is in ReviewConfiguration class.
	


**Mandatory Exercise 2: Consume Review REST service in Book REST service** 

- Get familiar with [RestTemplate.exchange](https://github.com/bsodzik/distributed-java-intro/tree/master/9-rest-consumption)
- */books/{isbn}* method should return book with related reviews
	- In BookConfiguration add RestTemplate bean
		
			@Bean
			public RestTemplate restTemplate(){
				RestTemplate restTemplate = new RestTemplate();
				return restTemplate;
			}
	
	- Create Review class in *books* module with isbn, title, text properties (remember about get* and set* methods). We want to ignore unknown properties so we need to add [@JsonIgnoreProperties](http://fasterxml.github.io/jackson-annotations/javadoc/2.1.0/com/fasterxml/jackson/annotation/JsonIgnoreProperties.html)

			@JsonIgnoreProperties(ignoreUnknown = true)
			public class Review {
				...
			}

	- In Book class add *reviews* property (remember about get* and set* methods) we don't want to save this list in database so we need to add [@Transient](http://docs.oracle.com/javaee/6/api/javax/persistence/Transient.html) annotation.

			public class Book {
				...		
				@Transient
				private Iterable<Review> reviews;
				...
			}

	- Create ReviewService interface:

			public interface ReviewService {
				Iterable<Review> getReviews(String isbn);
			}


	- Create ReviewServiceImpl and implement ReviewService interface autowrite RestTemplate and use [RestTemplate.exchange](https://github.com/bsodzik/distributed-java-intro/tree/master/9-rest-consumption)
			
			@Service
			public class ReviewServiceImpl implements ReviewService {

				@Autowired
				private RestTemplate restTemplate;

				@Override
				public Iterable<Review> getReviews(String isbn) {
					ParameterizedTypeReference<Iterable<Review>> responseType = ...;

					ResponseEntity<Iterable<Review>> entity = restTemplate.exchange(...);
                    ...
					return entity.getBody();
				}
			}
	
	- In BookController Autowired ReviewService 
	- In BookController.getByIsbn method add reviewService.getReviews(isbn) call and set reviews in Book class

**Optional Exercise 3: Add Cache in Book REST service**

We want "speed up" communication with *Review RESTful web service* by adding cache in *Book RESTful Web Service*. Spring provide easy to use [cache abstraction](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html). We will follow with simple [Guava cache](https://code.google.com/p/guava-libraries/wiki/CachesExplained). In our example we need to know how to:
- Add cache on method with [@Cacheable](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html#cache-annotations-cacheable) annotation
- Enable cache in configuration with [@EnableCaching](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html#cache-annotation-enable) annotation
- Add [GuavaCacheManager](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html#cache-store-configuration-guava) bean

In ReviewServiceImpl:
- Add @Cacheable("reviews") on getReviews method
- In BookConfiguration add @EnableCaching annotation and CacheManager bean

			@Bean
			public CacheManager cacheManager(){
				CacheBuilder cacheBuilder =  CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.SECONDS);
				GuavaCacheManager cacheManager = new GuavaCacheManager("reviews");
				cacheManager.setCacheBuilder(cacheBuilder);

				return cacheManager;
			}

- Test solution

**Optional Extra: Jackson Serialization View Support**
- As a user I want see reviews in book json only for GET /books/{isbn}
- Hint: [Jackson Serialization View Support](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-jsonview)

