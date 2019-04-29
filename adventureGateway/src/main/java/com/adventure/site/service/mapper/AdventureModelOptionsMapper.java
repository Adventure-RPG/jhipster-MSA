package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureModelOptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureModelOptions and its DTO AdventureModelOptionsDTO.
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
