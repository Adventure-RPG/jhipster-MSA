package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureRaceOptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureRaceOptions and its DTO AdventureRaceOptionsDTO.
 */
@Mapper(componentModel = "spring", uses = {AdventureModelOptionsMapper.class, AdventureModelMapper.class})
public interface AdventureRaceOptionsMapper extends EntityMapper<AdventureRaceOptionsDTO, AdventureRaceOptions> {

    @Mapping(source = "adventureModelOptions.id", target = "adventureModelOptionsId")
    AdventureRaceOptionsDTO toDto(AdventureRaceOptions adventureRaceOptions);

    @Mapping(source = "adventureModelOptionsId", target = "adventureModelOptions")
    AdventureRaceOptions toEntity(AdventureRaceOptionsDTO adventureRaceOptionsDTO);

    default AdventureRaceOptions fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureRaceOptions adventureRaceOptions = new AdventureRaceOptions();
        adventureRaceOptions.setId(id);
        return adventureRaceOptions;
    }
}
