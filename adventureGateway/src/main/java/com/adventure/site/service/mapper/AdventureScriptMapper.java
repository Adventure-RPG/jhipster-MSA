package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureScriptDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureScript and its DTO AdventureScriptDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureScriptMapper extends EntityMapper<AdventureScriptDTO, AdventureScript> {


}
