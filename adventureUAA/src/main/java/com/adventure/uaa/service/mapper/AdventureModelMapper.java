package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureModelDTO;

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

    default AdventureModel fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureModel adventureModel = new AdventureModel();
        adventureModel.setId(id);
        return adventureModel;
    }
}
