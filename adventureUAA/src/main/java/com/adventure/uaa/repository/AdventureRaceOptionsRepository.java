package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureRaceOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AdventureRaceOptions entity.
 */
@Repository
public interface AdventureRaceOptionsRepository extends JpaRepository<AdventureRaceOptions, Long> {

    @Query(value = "select distinct adventureRaceOptions from AdventureRaceOptions adventureRaceOptions left join fetch adventureRaceOptions.adventureModels",
        countQuery = "select count(distinct adventureRaceOptions) from AdventureRaceOptions adventureRaceOptions")
    Page<AdventureRaceOptions> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct adventureRaceOptions from AdventureRaceOptions adventureRaceOptions left join fetch adventureRaceOptions.adventureModels")
    List<AdventureRaceOptions> findAllWithEagerRelationships();

    @Query("select adventureRaceOptions from AdventureRaceOptions adventureRaceOptions left join fetch adventureRaceOptions.adventureModels where adventureRaceOptions.id =:id")
    Optional<AdventureRaceOptions> findOneWithEagerRelationships(@Param("id") Long id);

}
