package com.adventure.site.repository;

import com.adventure.site.domain.AdventureSkill;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureSkillRepository extends <AdventureSkill, Long> {

}
