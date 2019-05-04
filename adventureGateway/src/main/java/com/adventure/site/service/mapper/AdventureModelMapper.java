package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureModel} and its DTO {@link AdventureModelDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdventureCategoryTypeMapper.class})
public interface AdventureModelMapper extends EntityMapper<AdventureModelDTO, AdventureModel> {

    @Mapping(source = "adventureCategoryType.id", target = "adventureCategoryTypeId")
    AdventureModelDTO toDto(AdventureModel adventureModel);

    @Mapping(source = "adventureCategoryTypeId", target = "adventureCategoryType")
    @Mapping(target = "adventureRaceOptions", ignore = true)
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
