package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureCategoryTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureCategoryType} and its DTO {@link AdventureCategoryTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureCategoryTypeMapper extends EntityMapper<AdventureCategoryTypeDTO, AdventureCategoryType> {



    default AdventureCategoryType fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureCategoryType adventureCategoryType = new AdventureCategoryType();
        adventureCategoryType.setId(id);
        return adventureCategoryType;
    }
}
