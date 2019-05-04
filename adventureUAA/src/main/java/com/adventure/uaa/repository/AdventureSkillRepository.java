package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureSkill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureSkillRepository extends JpaRepository<AdventureSkill, Long> {

}
