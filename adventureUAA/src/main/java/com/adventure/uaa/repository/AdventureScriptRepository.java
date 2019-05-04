package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureScript;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureScript entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureScriptRepository extends JpaRepository<AdventureScript, Long> {

}
