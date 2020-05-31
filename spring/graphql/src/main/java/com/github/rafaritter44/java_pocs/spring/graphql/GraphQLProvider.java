package com.github.rafaritter44.java_pocs.spring.graphql;

import java.io.IOException;
import java.net.URL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

@Configuration
public class GraphQLProvider {
	
	private final GraphQLDataFetchers dataFetchers;
	
	public GraphQLProvider(final GraphQLDataFetchers dataFetchers) {
		this.dataFetchers = dataFetchers;
	}
	
	@Bean
	public GraphQL graphQL() throws IOException {
		final URL url = Resources.getResource("schema.graphqls");
		final String sdl = Resources.toString(url, Charsets.UTF_8);
		final TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
		final RuntimeWiring runtimeWiring = RuntimeWiring
				.newRuntimeWiring()
				.type(TypeRuntimeWiring
						.newTypeWiring("Query")
						.dataFetcher("bookById", dataFetchers.getBookByIdDataFetcher())
						.dataFetcher("booksByAuthorId", dataFetchers.getBooksByAuthorIdDataFetcher()))
				.type(TypeRuntimeWiring
						.newTypeWiring("Mutation")
						.dataFetcher("newAuthor", dataFetchers.getNewAuthorDataFetcher())
						.dataFetcher("newBook", dataFetchers.getNewBookDataFetcher()))
				.type(TypeRuntimeWiring
						.newTypeWiring("Book")
						.dataFetcher("author", dataFetchers.getAuthorDataFetcher()))
				.build();
		final SchemaGenerator schemaGenerator = new SchemaGenerator();
		final GraphQLSchema schema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
		return GraphQL.newGraphQL(schema).build();
	}
	
}
