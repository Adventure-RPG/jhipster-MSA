package com.adventure.core.service.mapper;

import com.adventure.core.domain.*;
import com.adventure.core.service.dto.AdventureRaceOptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureRaceOptions} and its DTO {@link AdventureRaceOptionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdventureModelOptionsMapper.class, AdventureModelMapper.class})
public interface AdventureRaceOptionsMapper extends EntityMapper<AdventureRaceOptionsDTO, AdventureRaceOptions> {

    @Mapping(source = "adventureModelOptions.id", target = "adventureModelOptionsId")
    AdventureRaceOptionsDTO toDto(AdventureRaceOptions adventureRaceOptions);

    @Mapping(source = "adventureModelOptionsId", target = "adventureModelOptions")
    AdventureRaceOptions toEntity(AdventureRaceOptionsDTO adventureRaceOptionsDTO);

    default AdventureRaceOptions fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureRaceOptions adventureRaceOptions = new AdventureRaceOptions();
        adventureRaceOptions.setId(id);
        return adventureRaceOptions;
    }
}
