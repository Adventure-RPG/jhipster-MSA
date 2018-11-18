package com.adventure.site.repository;

import com.adventure.site.domain.AdventureRaceOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureRaceOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureRaceOptionsRepository extends <AdventureRaceOptions, Long> {

}
