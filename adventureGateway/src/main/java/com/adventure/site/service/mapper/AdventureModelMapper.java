package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureModel and its DTO AdventureModelDTO.
 */
@Mapper(componentModel = "spring", uses = {AdventureCategoryTypeMapper.class})
public interface AdventureModelMapper extends EntityMapper<AdventureModelDTO, AdventureModel> {

    @Mapping(source = "adventureCategoryType.id", target = "adventureCategoryTypeId")
    AdventureModelDTO toDto(AdventureModel adventureModel);

    @Mapping(source = "adventureCategoryTypeId", target = "adventureCategoryType")
    AdventureModel toEntity(AdventureModelDTO adventureModelDTO);
}
