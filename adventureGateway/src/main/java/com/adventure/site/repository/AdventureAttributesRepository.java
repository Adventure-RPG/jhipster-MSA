package com.adventure.site.repository;

import com.adventure.site.domain.AdventureAttributes;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureAttributesRepository extends <AdventureAttributes, Long> {

}
