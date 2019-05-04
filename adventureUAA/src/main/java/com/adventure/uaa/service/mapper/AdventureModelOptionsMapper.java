package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureModelOptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureModelOptions} and its DTO {@link AdventureModelOptionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureModelOptionsMapper extends EntityMapper<AdventureModelOptionsDTO, AdventureModelOptions> {



    default AdventureModelOptions fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureModelOptions adventureModelOptions = new AdventureModelOptions();
        adventureModelOptions.setId(id);
        return adventureModelOptions;
    }
}
