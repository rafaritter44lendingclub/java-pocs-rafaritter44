package com.github.rafaritter44.java_pocs.mockito.dao;

import java.util.List;

public interface PersonDAO {
	void add(String person);
	List<String> getAll();
}
