package com.adventure.site.repository;

import com.adventure.site.domain.AdventureSkill;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the AdventureSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureSkillRepository extends MongoRepository<AdventureSkill, String> {

}
