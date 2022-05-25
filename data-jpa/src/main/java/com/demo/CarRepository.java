package com.demo;

import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {

    Iterable<Car> findByMakeIgnoreCase(String make);
}
