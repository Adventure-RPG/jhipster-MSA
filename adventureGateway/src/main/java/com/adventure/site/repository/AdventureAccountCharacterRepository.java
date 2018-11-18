package com.adventure.site.repository;

import com.adventure.site.domain.AdventureAccountCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureAccountCharacter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureAccountCharacterRepository extends <AdventureAccountCharacter, Long> {

}
