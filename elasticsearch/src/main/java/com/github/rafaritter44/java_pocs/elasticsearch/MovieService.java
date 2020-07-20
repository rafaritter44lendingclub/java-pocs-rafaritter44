package com.github.rafaritter44.java_pocs.elasticsearch;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieService {
	
	public static final String INDEX = "movies";
	
	private static final TypeReference<Map<String, Object>> STRING_TO_OBJ_MAP = new TypeReference<Map<String, Object>>() {};
	
	private final RestHighLevelClient client;
	private final ObjectMapper mapper;
	
	public MovieService(final RestHighLevelClient client) {
		this.client = client;
		this.mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);
	}
	
	public String index(final Movie movie) {
		try {
			final byte[] json = mapper.writeValueAsBytes(movie);
			final IndexRequest request = new IndexRequest(INDEX);
			request.source(json, XContentType.JSON);
			final IndexResponse response = client.index(request, RequestOptions.DEFAULT);
			return response.getId();
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public String index(final String documentId, final Movie movie) {
		try {
			final byte[] json = mapper.writeValueAsBytes(movie);
			final IndexRequest request = new IndexRequest(INDEX);
			request.id(documentId);
			request.source(json, XContentType.JSON);
			final IndexResponse response = client.index(request, RequestOptions.DEFAULT);
			return response.getId();
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public boolean bulk(final Map<String, Movie> movies) {
		try {
			final BulkRequest request = new BulkRequest();
			movies
					.entrySet()
					.parallelStream()
					.map(kv -> {
						try {
							final byte[] json = mapper.writeValueAsBytes(kv.getValue());
							return new IndexRequest(INDEX)
									.id(kv.getKey())
									.source(json, XContentType.JSON);
						} catch (JsonProcessingException e) {
							throw new RuntimeException(e.getMessage(), e);
						}
					})
					.forEachOrdered(request::add);
			final BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
			return !response.hasFailures();
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public Optional<Movie> get(final String documentId) {
		try {
			final GetSourceRequest request = new GetSourceRequest(INDEX, documentId);
			final GetSourceResponse response = client.getSource(request, RequestOptions.DEFAULT);
			return Optional.ofNullable(mapper.convertValue(response.getSource(), Movie.class));
		} catch (final ElasticsearchStatusException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				return Optional.empty();
			}
			throw e;
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public Optional<Movie> update(final String documentId, final Movie movie) {
		try {
			final Map<String, Object> json = mapper.convertValue(movie, STRING_TO_OBJ_MAP);
			final UpdateRequest request = new UpdateRequest(INDEX, documentId);
			request.doc(json);
			request.fetchSource(true);
			final UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
			final Map<String, Object> updatedMovie = response.getGetResult().getSource();
			return Optional.ofNullable(mapper.convertValue(updatedMovie, Movie.class));
		} catch (final ElasticsearchStatusException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				return Optional.empty();
			}
			throw e;
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public boolean delete(final String documentId) {
		try {
			final DeleteRequest request = new DeleteRequest(INDEX, documentId);
			final DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
			return response.getResult() == Result.DELETED;
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public List<Movie> searchAsYouTypeMovieTitle(final String input) {
		final SearchRequest request = new SearchRequest(INDEX)
				.source(new SearchSourceBuilder()
						.query(QueryBuilders
								.multiMatchQuery(input, "title", "title._2gram", "title._3gram")
								.type(Type.BOOL_PREFIX))
						.size(5));
		try {
			final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
			return Arrays
					.stream(response.getHits().getHits())
					.map(SearchHit::getSourceAsMap)
					.map(source -> mapper.convertValue(source, Movie.class))
					.collect(Collectors.toList());
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
}
