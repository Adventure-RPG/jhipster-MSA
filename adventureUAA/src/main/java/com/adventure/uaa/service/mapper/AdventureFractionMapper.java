package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureFractionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureFraction and its DTO AdventureFractionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureFractionMapper extends EntityMapper<AdventureFractionDTO, AdventureFraction> {


    @Mapping(target = "adventureRaces", ignore = true)
    AdventureFraction toEntity(AdventureFractionDTO adventureFractionDTO);

    default AdventureFraction fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureFraction adventureFraction = new AdventureFraction();
        adventureFraction.setId(id);
        return adventureFraction;
    }
}
