package com.adventure.site.repository;

import com.adventure.site.domain.AdventureAccountCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the AdventureAccountCharacter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureAccountCharacterRepository extends MongoRepository<AdventureAccountCharacter, String> {
    @Query("{}")
    Page<AdventureAccountCharacter> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<AdventureAccountCharacter> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<AdventureAccountCharacter> findOneWithEagerRelationships(String id);

}
