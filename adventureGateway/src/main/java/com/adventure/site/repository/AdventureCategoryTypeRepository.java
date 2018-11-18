package com.adventure.site.repository;

import com.adventure.site.domain.AdventureCategoryType;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureCategoryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureCategoryTypeRepository extends <AdventureCategoryType, Long> {

}
