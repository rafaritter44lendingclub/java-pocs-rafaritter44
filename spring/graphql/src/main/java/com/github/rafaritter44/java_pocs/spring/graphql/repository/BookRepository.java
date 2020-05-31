package com.github.rafaritter44.java_pocs.spring.graphql.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.github.rafaritter44.java_pocs.spring.graphql.entity.Author;
import com.github.rafaritter44.java_pocs.spring.graphql.entity.Book;

@Repository
public class BookRepository {
	
	private final Map<UUID, Book> books = new HashMap<>();
	private final Map<UUID, Author> authors = new HashMap<>();
	
	public Optional<Book> getBookById(final UUID bookId) {
		return Optional.ofNullable(books.get(bookId));
	}
	
	public Optional<Author> getAuthorById(final UUID authorId) {
		return Optional.ofNullable(authors.get(authorId));
	}
	
	public List<Book> getBooksByAuthorId(final UUID authorId) {
		return books
				.values()
				.parallelStream()
				.filter(book -> book.getAuthorId().equals(authorId))
				.collect(Collectors.toList());
	}
	
	public Optional<Book> newBook(final String name, final int pageCount, final UUID authorId) {
		if (!authors.containsKey(authorId)) {
			return Optional.empty();
		}
		final Book book = new Book();
		book.setId(UUID.randomUUID());
		book.setAuthorId(authorId);
		book.setName(name);
		book.setPageCount(pageCount);
		books.put(book.getId(), book);
		return Optional.of(book);
	}
	
	public Author newAuthor(final String firstName, final String lastName) {
		final Author author = new Author();
		author.setId(UUID.randomUUID());
		author.setFirstName(firstName);
		author.setLastName(lastName);
		authors.put(author.getId(), author);
		return author;
	}
	
}
