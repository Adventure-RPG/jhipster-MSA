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
@Repository
public interface AdventureRaceRepository extends JpaRepository<AdventureRace, Long> {

    @Query(value = "select distinct adventureRace from AdventureRace adventureRace left join fetch adventureRace.adventureFractions",
        countQuery = "select count(distinct adventureRace) from AdventureRace adventureRace")
    Page<AdventureRace> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct adventureRace from AdventureRace adventureRace left join fetch adventureRace.adventureFractions")
    List<AdventureRace> findAllWithEagerRelationships();

    @Query("select adventureRace from AdventureRace adventureRace left join fetch adventureRace.adventureFractions where adventureRace.id =:id")
    Optional<AdventureRace> findOneWithEagerRelationships(@Param("id") Long id);

}
