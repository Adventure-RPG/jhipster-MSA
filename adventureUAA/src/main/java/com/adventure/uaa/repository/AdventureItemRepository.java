package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureItemRepository extends JpaRepository<AdventureItem, Long> {

}
