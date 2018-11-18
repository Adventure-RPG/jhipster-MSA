package com.adventure.site.repository;

import com.adventure.site.domain.AdventureModel;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureModelRepository extends <AdventureModel, Long> {

}
