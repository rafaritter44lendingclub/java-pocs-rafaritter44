package com.github.rafaritter44.java_pocs.datastax;

import java.math.BigDecimal;

import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.google.common.util.concurrent.ListenableFuture;

@Accessor
public interface CityAccessor {

	@Query("SELECT * FROM city")
	ListenableFuture<Result<City>> getAllAsync();
	
	@Query("INSERT INTO city (id, name, distance, to_id) VALUES (?, ?, ?, ?) IF NOT EXISTS")
	ResultSetFuture insertIfNotExists(Long id, String name, BigDecimal distance, Long to_id);
	
	@Query("DELETE FROM city WHERE id = ? AND to_id = ? IF EXISTS")
	ResultSetFuture deleteIfExists(Long id, Long to_id);
	
	@Query("UPDATE city SET name = :n, distance = :d WHERE id = :i AND to_id = :t IF EXISTS")
	ResultSetFuture updateIfExists(@Param("i") Long id, @Param("n") String name,
			@Param("d") BigDecimal distance, @Param("t") Long to_id);
	
}
