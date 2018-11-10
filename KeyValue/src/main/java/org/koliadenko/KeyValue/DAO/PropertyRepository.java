package org.koliadenko.KeyValue.DAO;

import org.koliadenko.KeyValue.domain.Property;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    List<Property> findByOwner(String owner);
}
