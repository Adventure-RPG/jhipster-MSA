package com.adventure.site.repository;

import com.adventure.site.domain.AdventureItem;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureItemRepository extends <AdventureItem, Long> {

}
