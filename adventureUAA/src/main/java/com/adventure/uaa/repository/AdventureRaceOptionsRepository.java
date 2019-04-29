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
@SuppressWarnings("unused")
@Repository
public interface AdventureRaceOptionsRepository extends JpaRepository<AdventureRaceOptions, Long> {

    @Query(value = "select distinct adventure_race_options from AdventureRaceOptions adventure_race_options left join fetch adventure_race_options.adventureModels",
        countQuery = "select count(distinct adventure_race_options) from AdventureRaceOptions adventure_race_options")
    Page<AdventureRaceOptions> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct adventure_race_options from AdventureRaceOptions adventure_race_options left join fetch adventure_race_options.adventureModels")
    List<AdventureRaceOptions> findAllWithEagerRelationships();

    @Query("select adventure_race_options from AdventureRaceOptions adventure_race_options left join fetch adventure_race_options.adventureModels where adventure_race_options.id =:id")
    Optional<AdventureRaceOptions> findOneWithEagerRelationships(@Param("id") Long id);

}
