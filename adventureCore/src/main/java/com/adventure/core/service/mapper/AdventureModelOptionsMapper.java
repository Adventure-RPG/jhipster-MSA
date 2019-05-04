package com.adventure.core.service.mapper;

import com.adventure.core.domain.*;
import com.adventure.core.service.dto.AdventureModelOptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureModelOptions} and its DTO {@link AdventureModelOptionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureModelOptionsMapper extends EntityMapper<AdventureModelOptionsDTO, AdventureModelOptions> {



    default AdventureModelOptions fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureModelOptions adventureModelOptions = new AdventureModelOptions();
        adventureModelOptions.setId(id);
        return adventureModelOptions;
    }
}
