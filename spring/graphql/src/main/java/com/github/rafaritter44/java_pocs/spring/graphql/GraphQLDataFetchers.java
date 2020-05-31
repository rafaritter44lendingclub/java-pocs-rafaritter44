package com.github.rafaritter44.java_pocs.spring.graphql;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.rafaritter44.java_pocs.spring.graphql.entity.Author;
import com.github.rafaritter44.java_pocs.spring.graphql.entity.Book;
import com.github.rafaritter44.java_pocs.spring.graphql.repository.BookRepository;

import graphql.schema.DataFetcher;

@Component
public class GraphQLDataFetchers {
	
	private final BookRepository bookRepository;
	
	public GraphQLDataFetchers(final BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public DataFetcher<Optional<Book>> getBookByIdDataFetcher() {
		return dataFetchingEnvironment -> {
			final UUID bookId = UUID.fromString(dataFetchingEnvironment.getArgument("id"));
			return bookRepository.getBookById(bookId);
		};
	}

	public DataFetcher<Optional<Author>> getAuthorDataFetcher() {
		return dataFetchingEnvironment -> {
			final Book book = dataFetchingEnvironment.getSource();
			final UUID authorId = book.getAuthorId();
			return bookRepository.getAuthorById(authorId);
		};
	}

	public DataFetcher<List<Book>> getBooksByAuthorIdDataFetcher() {
		return dataFetchingEnvironment -> {
			final UUID authorId = UUID.fromString(dataFetchingEnvironment.getArgument("authorId"));
			return bookRepository.getBooksByAuthorId(authorId);
		};
	}
	
	public DataFetcher<Optional<Book>> getNewBookDataFetcher() {
		return dataFetchingEnvironment -> {
			final String name = dataFetchingEnvironment.getArgument("name");
			final int pageCount = dataFetchingEnvironment.getArgument("pageCount");
			final UUID authorId = UUID.fromString(dataFetchingEnvironment.getArgument("authorId"));
			return bookRepository.newBook(name, pageCount, authorId);
		};
	}
	
	public DataFetcher<Author> getNewAuthorDataFetcher() {
		return dataFetchingEnvironment -> {
			final String firstName = dataFetchingEnvironment.getArgument("firstName");
			final String lastName = dataFetchingEnvironment.getArgument("lastName");
			return bookRepository.newAuthor(firstName, lastName);
		};
	}

}
