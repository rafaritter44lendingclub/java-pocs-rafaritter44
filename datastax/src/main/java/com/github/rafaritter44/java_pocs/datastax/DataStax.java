package com.github.rafaritter44.java_pocs.datastax;

import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.delete;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class DataStax {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		Cluster cluster = Cluster.builder()
				.addContactPoint("127.0.0.1")
				.withSocketOptions(new SocketOptions().setReadTimeoutMillis(60_000))
				.build();
		
		Session session = cluster.connect();
		
		session.execute("DROP KEYSPACE IF EXISTS cluster_test");
		session.execute("CREATE KEYSPACE cluster_test WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 }");
		session.execute("USE cluster_test");
		session.execute("CREATE TABLE city (id BIGINT, name TEXT, distance DECIMAL, to_id BIGINT, PRIMARY KEY (id, to_id))");
		
		PreparedStatement preparedStatement = session.prepare(
				insertInto("city")
				.value("id", bindMarker())
				.value("to_id", bindMarker())
				.value("distance", bindMarker())
				.value("name", bindMarker()));
		
		BoundStatement boundStatement = preparedStatement.bind()
				.setLong("id", 10L)
				.setLong("to_id", 20L)
				.setDecimal("distance", BigDecimal.valueOf(100L))
				.setString("name", "Gravatai");
		
		session.executeAsync(boundStatement);
		
		Thread.sleep(5000L);
		
		preparedStatement = session.prepare(select().all().from("city"));
		boundStatement = preparedStatement.bind();
		ListenableFuture<ResultSet> result = session.executeAsync(boundStatement);
		
		Futures.addCallback(result, new FutureCallback<ResultSet>() {
			public void onSuccess(ResultSet rs) {
				rs.forEach(row -> System.out.println(row.getLong("id") + " - " + row.getLong("to_id")));
			}
			public void onFailure(Throwable e) {
				System.err.println(e.getMessage());
			}
		});
		
		Thread.sleep(5000L);
		
		preparedStatement = session.prepare(
				delete().from("city")
				.where(eq("id", bindMarker()))
				.and(eq("to_id", bindMarker())));
		
		boundStatement = preparedStatement.bind()
				.setLong("id", 10L)
				.setLong("to_id", 20L);
		
		session.executeAsync(boundStatement);
		
		Thread.sleep(5000L);
		
		preparedStatement = session.prepare(select().all().from("city"));
		boundStatement = preparedStatement.bind();
		result = session.executeAsync(boundStatement);
		
		Futures.addCallback(result, new FutureCallback<ResultSet>() {
			public void onSuccess(ResultSet rs) {
				rs.forEach(row -> System.out.println(row.getLong("id") + " - " + row.getLong("to_id")));
			}
			public void onFailure(Throwable e) {
				System.err.println(e.getMessage());
			}
		});
		
		Thread.sleep(5000L);
		
		MappingManager mappingManager = new MappingManager(session);
		Mapper<City> mapper = mappingManager.mapper(City.class);
		CityAccessor cityAccessor = mappingManager.createAccessor(CityAccessor.class);
		
		mapper.save(new City(3L, "Icara", BigDecimal.valueOf(1.5), 4L));
		cityAccessor.getAllAsync().get().forEach(city -> System.out.println(city.getName()));
		mapper.delete(3L, 4L);
		cityAccessor.getAllAsync().get().forEach(city -> System.out.println(city.getName()));
		
		System.out.println(cityAccessor.insertIfNotExists(5L, "City", BigDecimal.valueOf(2.5), 9L).get().wasApplied());
		System.out.println(cityAccessor.insertIfNotExists(5L, "City", BigDecimal.valueOf(2.5), 9L).get().wasApplied());
		System.out.println(cityAccessor.updateIfExists(5L, "City updated", BigDecimal.valueOf(2.6), 9L).get().wasApplied());
		System.out.println(cityAccessor.deleteIfExists(5L, 9L).get().wasApplied());
		
		session.execute("DROP KEYSPACE cluster_test");
		
		session.close();
		cluster.close();		
	}

}
