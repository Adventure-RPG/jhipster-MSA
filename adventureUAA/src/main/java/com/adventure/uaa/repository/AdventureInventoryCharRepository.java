package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureInventoryChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureInventoryChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureInventoryCharRepository extends JpaRepository<AdventureInventoryChar, Long> {

}
