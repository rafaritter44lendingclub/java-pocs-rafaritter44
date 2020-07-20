package com.github.rafaritter44.java_pocs.elasticsearch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;

public class Indexer {
	
	public static void main(final String[] args) {
		
		try (final RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")))) {
			
			// CREATE INDEX
			final CreateIndexRequest createIndexRequest = new CreateIndexRequest(MovieService.INDEX);
			createIndexRequest.mapping(
					"{\n" + 
					"       \"properties\": {\n" + 
					"           \"title\": {\n" + 
					"               \"type\": \"search_as_you_type\"\n" + 
					"           },\n" + 
					"           \"genre\": {\n" + 
					"               \"type\": \"search_as_you_type\"\n" + 
					"           }\n" + 
					"       }\n" + 
					"   }",
					XContentType.JSON);
			final CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
			System.out.printf("%s index creation acknowledged: %b\n", MovieService.INDEX, createIndexResponse.isAcknowledged());
			
			// INDEX MOVIES DATA
			final Map<String, Movie> movies = new HashMap<>();
			try (final Stream<String> lines = Files.lines(Paths.get("movies.csv"))) {
				lines
						.skip(1L)
						.map(line -> line.replace("\"", ""))
						.filter(line -> line.matches("[1-9][0-9]{0,6},.* \\([0-9]{4}\\),.*"))
						.map(line -> line.split("(?<=[1-9][0-9]{0,6}),|(?<=.{1,100} \\([0-9]{4}\\)),"))
						.filter(splitLine -> splitLine.length == 3)
						.forEach(splitLine -> {
							final String movieId = splitLine[0];
							final String movieTitleAndYear = splitLine[1];
							final String movieTitle = movieTitleAndYear.substring(0, movieTitleAndYear.length() - 7);
							final Integer movieYear = Integer
									.parseInt(movieTitleAndYear
											.substring(movieTitleAndYear.length() - 4, movieTitleAndYear.length() - 1));
							final String[] movieGenres = "(no genres listed)".equals(splitLine[2])
									? new String[0]
									: splitLine[2].split("\\|");
							final Movie movie = new Movie();
							movie.setTitle(movieTitle);
							movie.setYear(movieYear);
							movie.setGenres(Arrays.asList(movieGenres));
							movies.put(movieId, movie);
						});
			}
			final boolean result = new MovieService(client).bulk(movies);
			System.out.printf("all movies indexed successfully: %b", result);
			
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
