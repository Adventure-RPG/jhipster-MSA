package com.adventure.site.repository;

import com.adventure.site.domain.AdventureInventoryChar;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureInventoryChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureInventoryCharRepository extends <AdventureInventoryChar, Long> {

}
