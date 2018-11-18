package com.adventure.site.repository;

import com.adventure.site.domain.AdventureRace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureRace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureRaceRepository extends <AdventureRace, Long> {

}
