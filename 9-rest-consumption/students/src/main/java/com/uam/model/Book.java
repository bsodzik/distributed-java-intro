package com.uam.model;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {

	public static final String URL = "http://bsodzik.herokuapp.com/books/";

	private String isbn;
	private String title;
	private String description;
	private List<String> authors;

	public Book() {
	}

	public Book(String isbn, String title, String description, String... authors) {
		this.isbn = isbn;
		this.title = title;
		this.description = description;
		this.authors = Arrays.asList(authors);
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Book book = (Book) o;
		if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return isbn != null ? isbn.hashCode() : 0;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Book[");
		sb.append("isbn=").append(isbn);
		sb.append(", title=").append(title);
		sb.append(", description=").append(description);
		sb.append(", authors=").append(authors);
		sb.append(']');
		return sb.toString();
	}
}
