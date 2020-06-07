package com.github.rafaritter44.java_pocs.elasticsearch;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class Application {
	
	public static void main(final String[] args) {
		
		try (final RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")))) {
			
			final MovieService service = new MovieService(client);
			
			// CREATE
			final Movie movie = new Movie();
			movie.setTitle("Shutter Island");
			movie.setYear(2010);
			movie.setGenres(Arrays.asList("Thriller"));
			final String documentId = service.index(movie);
			System.out.println("document id: " + documentId);
			
			// READ
			System.out.println("created movie: " + service.get(documentId));
			
			// UPDATE
			final Movie partiallyUpdatedMovie = new Movie();
			partiallyUpdatedMovie.setGenres(Arrays.asList("Thriller", "Mistery"));
			final Optional<Movie> fullyUpdatedMovie = service.update(documentId, partiallyUpdatedMovie);
			System.out.println("updated movie: " + fullyUpdatedMovie);
			
			// DELETE
			System.out.println("movie deleted: " + service.delete(documentId));
			
			// READ 404
			final String nonexistentId = "nonexistent_id";
			System.out.println("read 404 present: " + service.get(nonexistentId).isPresent());
			
			// UPDATE 404
			System.out.println("update 404 present: " + service.update(nonexistentId, new Movie()).isPresent());
			
			// DELETE 404
			System.out.println("delete 404 present: " + service.delete(nonexistentId));
			
			// DELETE INDEX
			final DeleteIndexRequest request = new DeleteIndexRequest(MovieService.INDEX);
			final AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
			System.out.printf("%s index deletion acknowledged: %b", MovieService.INDEX, response.isAcknowledged());
			
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
