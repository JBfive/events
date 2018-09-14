package com.jym.events.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jym.events.models.Event;

public interface EventRepository extends CrudRepository<Event, Long> {
	List<Event> findAll();
	List<Event> findByState(String state);
	List<Event> findByStateNot(String state);
}
