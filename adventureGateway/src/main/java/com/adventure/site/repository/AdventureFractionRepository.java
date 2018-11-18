package com.adventure.site.repository;

import com.adventure.site.domain.AdventureFraction;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureFraction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureFractionRepository extends <AdventureFraction, Long> {

}
