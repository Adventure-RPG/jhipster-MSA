package com.adventure.site.repository;

import com.adventure.site.domain.AdventureInventoryChar;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the AdventureInventoryChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureInventoryCharRepository extends MongoRepository<AdventureInventoryChar, String> {

}
