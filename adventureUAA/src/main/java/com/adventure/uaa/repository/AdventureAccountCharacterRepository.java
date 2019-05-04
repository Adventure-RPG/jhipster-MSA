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
@Repository
public interface AdventureAccountCharacterRepository extends JpaRepository<AdventureAccountCharacter, Long> {

    @Query(value = "select distinct adventureAccountCharacter from AdventureAccountCharacter adventureAccountCharacter left join fetch adventureAccountCharacter.adventureSkills",
        countQuery = "select count(distinct adventureAccountCharacter) from AdventureAccountCharacter adventureAccountCharacter")
    Page<AdventureAccountCharacter> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct adventureAccountCharacter from AdventureAccountCharacter adventureAccountCharacter left join fetch adventureAccountCharacter.adventureSkills")
    List<AdventureAccountCharacter> findAllWithEagerRelationships();

    @Query("select adventureAccountCharacter from AdventureAccountCharacter adventureAccountCharacter left join fetch adventureAccountCharacter.adventureSkills where adventureAccountCharacter.id =:id")
    Optional<AdventureAccountCharacter> findOneWithEagerRelationships(@Param("id") Long id);

}
