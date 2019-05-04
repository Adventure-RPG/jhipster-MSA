package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureAttributesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureAttributes} and its DTO {@link AdventureAttributesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureAttributesMapper extends EntityMapper<AdventureAttributesDTO, AdventureAttributes> {



    default AdventureAttributes fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureAttributes adventureAttributes = new AdventureAttributes();
        adventureAttributes.setId(id);
        return adventureAttributes;
    }
}
