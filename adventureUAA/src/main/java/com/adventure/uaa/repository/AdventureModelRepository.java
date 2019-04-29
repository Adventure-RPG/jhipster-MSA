package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureModelRepository extends JpaRepository<AdventureModel, Long> {

}
