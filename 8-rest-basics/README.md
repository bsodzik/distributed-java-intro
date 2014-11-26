## Labs 8 - REST - basics ##


### Goal ###
- Refresh information about HTTP protocol
- Understand basic concepts of REST architecture  
- Get familiar with *curl*
- Get familiar with *REST Client* for Firefox or *Postman* for Chrome

### Realization ###


**Exercise 1**

Hypertext Transfer Protocol ([HTTP](https://tools.ietf.org/html/rfc2616)) is an application-level protocol for distributed, collaborative, hypermedia information systems. It is a generic, stateless, protocol which can be used for many tasks beyond its use for hypertext.

Main features of HTTP

- request-response stateless architecture
- relies on TCP/IP as transport protocol
- resources identified and located by Uniform Resource Locators (URL)
- current version is HTTP/1.1
- HTTPS is a secure version of HTTP based on SSL/TLS protocol

Exemplary HTTP request

	GET /index.html HTTP/1.1
	Host: www.w3.org

Exemplary HTTP response

	HTTP/1.1 200 OK
	Date: Wed, 26 Nov 2014 20:26:04 GMT
	Server: Apache/2
	Content-Length: 27163
	Content-Type: text/html; charset=iso-8859-1
	
	<html lang="en-US">
	<head>
	    <title>HTTP - Hypertext Transfer Protocol Overview</title>
	</head>
	<body>...</body>
	</html>

To finish this exercise load resource identified by URL [http://www.w3.org/Protocols/](http://www.w3.org/Protocols/) using command line client.

1. Use *curl* to load content. Execute following command in GitBash

		curl http://www.w3.org/Protocols/

2. Add `-D -` argument to display whole HTTP response. Execute following command

		curl -D - http://www.w3.org/Protocols/

3. If you feel strong load content of the same URL using *telnet*.


**Exercise 2**

Representational state transfer (REST) is an architectural style strongly based on HTTP protocol. Its main application is in the area of web services. However, in contrary to traditional SOAP-based web services, REST services are very lightweight, uncomplicated and flexible.

Main concepts of REST are *resources*, *operations*, *media types* and *status codes*.

- Resources can be seen as entities. They can exist in a tree structure - one resource may have sub-resources. Resources are identified by URL fragment called `path`. Exemplary resources in hypothetical system are
	- /users - all users
	- /users/1337 - particular user identified by 1337 id
	- /users/1337/addresses - list of addresses assigned to user 1337
	- /users/1337/addresses/home - information about address identified as home
	- /users/1337/addresses/office - information about address identified as office  
- Operations can be executed on particular resources. They are represented by HTTP `method`. There are four basic operations (covering CRUD acronym) represented by following HTTP methods
	- GET - retrieve list of elements or single element
	- POST - create new element
	- PUT - modify existing element
	- DELETE - delete existing element
- Media types define how to present (visualize) resources. They are represented by HTTP `headers`: `Accept` and `Content-Type`. Two most commonly used media types are
	- JSON - [JavaScript Object Notation](http://json.org/) is a lightweight text data format easy for computers to parse and generate as well as easy for humans to read. It is defined as `application/json`
	- XML - [Extensible Markup Language](http://www.w3.org/XML/) is another text data format commonly used for exchanging data between systems. It is defined as `application/xml`
- Status codes correspond to statuses of executed operations. The most common status code is 200 meaning request was successful. If data is not found, status code 404 is returned. Whole list of HTTP status codes is available [here](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html).

The goal of this task is to access Online Book Store available under URL [http://bsodzik.herokuapp.com/books](http://bsodzik.herokuapp.com/books). There is only one resource `/books` which can be represented as both JSON and XML. All REST operations are available. Have special attention to HTTP response headers and status codes.

You should start with *curl* command line tool. When you feel confident with it, install *REST Client* plugin for Firefox or *Postman* extension for Chrome and get familiar with them.

To find more details about Online Book Store project check source code, especially [BookResource](https://github.com/bsodzik/rest-showcase/blob/master/src/main/java/com/bsodzik/resources/BookResource.java) class.

1. Execute several GET operations (`Accept` header is used to determine output media type)
	- Retrieve list of all books as JSON

			curl -H 'Accept: application/json' -X GET -D - http://bsodzik.herokuapp.com/books
	- Retrieve list of all books as XML

			curl -H 'Accept: application/xml' -X GET -D - http://bsodzik.herokuapp.com/books

	- Search book of given author (use HTTP `query parameter` for that)

			curl -H 'Accept: application/json' -X GET -D - http://bsodzik.herokuapp.com/books?author=George%20Martin

	- Retrieve book for given ISBN number

			curl -H 'Accept: application/json' -X GET -D - http://bsodzik.herokuapp.com/books/0749309423

2. Add your own books using POST operation (`Content-Type` header is used to determine input media type)
	- Add new book using JSON media type
	
			curl -H 'Accept: application/json' -H 'Content-Type: application/json' -X POST -d '{"isbn":"1234567890","title":"Be creative","description":"Book for people who are more than copy and paste..","authors":["Captain Obvious", "Tony Montana"]}' -D - http://bsodzik.herokuapp.com/books

	- Add new book using XML media type

			curl -H 'Accept: application/xml' -H 'Content-Type: application/xml' -X POST -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><book><authors>Lucinda Dykes</authors><authors>Ed Tittel</authors><description>Oh dear, someone really wrote such book!</description><isbn>0764588451</isbn><title>XML For Dummies</title></book>' -D - http://bsodzik.herokuapp.com/books

3. Modify added books using PUT operation (requests similar to POST)
4. Delete added books using DELETE operation (requests similar to GET)

**Exercise 3**

REST services can take all advantages of HTTP protocol. Two of such extra features are GZIP compression and ETag caching.

- GZIP compression reduces amount of data sent over the wire. Compression algorithm can be used on both client and server side reducing both request and response size. To enable GZIP compression client must add `Accept-Encoding` header with value `gzip` to HTTP request.
- ETag is a special hash value calculated on server side. It is calculated based on data to be sent using for example MD5 algorithm. Client can cache ETag for particular request and when is sending the same request again, ETag can be sent as value of `If-None-Match` header. Server then calculates the hash again and compares it with ETag attached by client. If they are equals no data is transmitted and status code 304 is returned (meaning Not Modified).

To accomplish this exercise execute following objectives 

1. Return data using GZIP compression
	- Retrieve all books as JSON and save data to *books.zip* file using following command

			curl -H 'Accept: application/json' -H 'Accept-Encoding: gzip' -X GET http://bsodzik.herokuapp.com/books > books.zip

	- Verify that zip file is not corrupted
	- Remove `Accept-Encoding` header and save the same data to *books.txt* file and compare sizes

2. Cache data using ETag
	- Prepare URL for retrieving book for particular title (e.g. /books?title=Shining)
	- Retrieve data for prepared URL and copy value of `Etag` response header
	- Add `If-None-Match` header to your previous request with value equal to copied ETag surrounded with double quotes. Exemplary *curl* command looks following

			curl -H 'Accept: application/json' -H 'If-None-Match: "b92392928ea957208b0d1bfb212d1bc0"' -X GET -D - http://bsodzik.herokuapp.com/books?title=Shining
	- Observe returned status code