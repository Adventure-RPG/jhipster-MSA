package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureSkillDTO;

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

    default AdventureSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureSkill adventureSkill = new AdventureSkill();
        adventureSkill.setId(id);
        return adventureSkill;
    }
}
