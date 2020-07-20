package com.github.rafaritter44.java_pocs.elasticsearch;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class IndexDeleter {
	
	public static void main(final String[] args) {
		
		try (final RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")))) {
			final DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(MovieService.INDEX);
			final AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
			System.out.printf("%s index deletion acknowledged: %b", MovieService.INDEX, deleteIndexResponse.isAcknowledged());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
