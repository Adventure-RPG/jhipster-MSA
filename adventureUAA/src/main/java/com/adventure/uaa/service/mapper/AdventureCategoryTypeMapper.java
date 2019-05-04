package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureCategoryTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureCategoryType} and its DTO {@link AdventureCategoryTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureCategoryTypeMapper extends EntityMapper<AdventureCategoryTypeDTO, AdventureCategoryType> {



    default AdventureCategoryType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureCategoryType adventureCategoryType = new AdventureCategoryType();
        adventureCategoryType.setId(id);
        return adventureCategoryType;
    }
}
