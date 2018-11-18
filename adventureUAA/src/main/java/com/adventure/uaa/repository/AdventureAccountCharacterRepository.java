package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureAccountCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AdventureAccountCharacter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureAccountCharacterRepository extends JpaRepository<AdventureAccountCharacter, Long> {

    @Query(value = "select distinct adventure_account_character from AdventureAccountCharacter adventure_account_character left join fetch adventure_account_character.adventureSkills",
        countQuery = "select count(distinct adventure_account_character) from AdventureAccountCharacter adventure_account_character")
    Page<AdventureAccountCharacter> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct adventure_account_character from AdventureAccountCharacter adventure_account_character left join fetch adventure_account_character.adventureSkills")
    List<AdventureAccountCharacter> findAllWithEagerRelationships();

    @Query("select adventure_account_character from AdventureAccountCharacter adventure_account_character left join fetch adventure_account_character.adventureSkills where adventure_account_character.id =:id")
    Optional<AdventureAccountCharacter> findOneWithEagerRelationships(@Param("id") Long id);

}
