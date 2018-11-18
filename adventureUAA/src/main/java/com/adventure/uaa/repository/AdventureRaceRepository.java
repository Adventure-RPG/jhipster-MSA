package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureRace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AdventureRace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureRaceRepository extends JpaRepository<AdventureRace, Long> {

    @Query(value = "select distinct adventure_race from AdventureRace adventure_race left join fetch adventure_race.adventureFractions",
        countQuery = "select count(distinct adventure_race) from AdventureRace adventure_race")
    Page<AdventureRace> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct adventure_race from AdventureRace adventure_race left join fetch adventure_race.adventureFractions")
    List<AdventureRace> findAllWithEagerRelationships();

    @Query("select adventure_race from AdventureRace adventure_race left join fetch adventure_race.adventureFractions where adventure_race.id =:id")
    Optional<AdventureRace> findOneWithEagerRelationships(@Param("id") Long id);

}
