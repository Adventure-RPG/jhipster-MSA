package com.adventure.site.repository;

import com.adventure.site.domain.AdventureCategoryType;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the AdventureCategoryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureCategoryTypeRepository extends MongoRepository<AdventureCategoryType, String> {

}
