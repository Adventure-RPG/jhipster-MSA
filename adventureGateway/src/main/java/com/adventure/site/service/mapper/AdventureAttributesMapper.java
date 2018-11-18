package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureAttributesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureAttributes and its DTO AdventureAttributesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureAttributesMapper extends EntityMapper<AdventureAttributesDTO, AdventureAttributes> {


}
