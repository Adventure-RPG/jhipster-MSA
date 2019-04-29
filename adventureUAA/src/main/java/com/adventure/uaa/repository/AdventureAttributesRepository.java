package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureAttributesRepository extends JpaRepository<AdventureAttributes, Long> {

}
