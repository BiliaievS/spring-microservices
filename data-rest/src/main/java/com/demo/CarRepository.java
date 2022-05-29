package com.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface CarRepository extends CrudRepository<Car, Long> {

    @RestResource(path = "find")
    Iterable<Car> findByMakeIgnoreCase(@Param("make") String make);
}
