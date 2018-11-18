package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureRaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureRace and its DTO AdventureRaceDTO.
 */
@Mapper(componentModel = "spring", uses = {AdventureRaceOptionsMapper.class, AdventureFractionMapper.class})
public interface AdventureRaceMapper extends EntityMapper<AdventureRaceDTO, AdventureRace> {

    @Mapping(source = "adventureRaceOptions.id", target = "adventureRaceOptionsId")
    AdventureRaceDTO toDto(AdventureRace adventureRace);

    @Mapping(source = "adventureRaceOptionsId", target = "adventureRaceOptions")
    AdventureRace toEntity(AdventureRaceDTO adventureRaceDTO);

    default AdventureRace fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureRace adventureRace = new AdventureRace();
        adventureRace.setId(id);
        return adventureRace;
    }
}
