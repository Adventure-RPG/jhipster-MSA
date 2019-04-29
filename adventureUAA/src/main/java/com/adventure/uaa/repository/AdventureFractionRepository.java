package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureFraction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureFraction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureFractionRepository extends JpaRepository<AdventureFraction, Long> {

}
