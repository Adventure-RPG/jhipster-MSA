package com.adventure.core.service.mapper;

import com.adventure.core.domain.*;
import com.adventure.core.service.dto.AdventureModelDTO;

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

    default AdventureModel fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureModel adventureModel = new AdventureModel();
        adventureModel.setId(id);
        return adventureModel;
    }
}
