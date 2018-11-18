package com.adventure.core.service.mapper;

import com.adventure.core.domain.*;
import com.adventure.core.service.dto.AdventureSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureSkill and its DTO AdventureSkillDTO.
 */
@Mapper(componentModel = "spring", uses = {AdventureScriptMapper.class})
public interface AdventureSkillMapper extends EntityMapper<AdventureSkillDTO, AdventureSkill> {

    @Mapping(source = "adventureScript.id", target = "adventureScriptId")
    AdventureSkillDTO toDto(AdventureSkill adventureSkill);

    @Mapping(source = "adventureScriptId", target = "adventureScript")
    AdventureSkill toEntity(AdventureSkillDTO adventureSkillDTO);

    default AdventureSkill fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureSkill adventureSkill = new AdventureSkill();
        adventureSkill.setId(id);
        return adventureSkill;
    }
}
