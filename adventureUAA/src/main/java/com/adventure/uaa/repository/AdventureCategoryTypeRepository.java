package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureCategoryType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureCategoryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureCategoryTypeRepository extends JpaRepository<AdventureCategoryType, Long> {

}
