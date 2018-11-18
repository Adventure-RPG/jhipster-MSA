package com.adventure.site.repository;

import com.adventure.site.domain.AdventureScript;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureScript entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureScriptRepository extends <AdventureScript, Long> {

}
