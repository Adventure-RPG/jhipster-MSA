package com.adventure.core.service.mapper;

import com.adventure.core.domain.*;
import com.adventure.core.service.dto.AdventureScriptDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureScript} and its DTO {@link AdventureScriptDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureScriptMapper extends EntityMapper<AdventureScriptDTO, AdventureScript> {



    default AdventureScript fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureScript adventureScript = new AdventureScript();
        adventureScript.setId(id);
        return adventureScript;
    }
}
