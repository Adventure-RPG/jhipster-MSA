package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureModelOptions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureModelOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureModelOptionsRepository extends JpaRepository<AdventureModelOptions, Long> {

}
