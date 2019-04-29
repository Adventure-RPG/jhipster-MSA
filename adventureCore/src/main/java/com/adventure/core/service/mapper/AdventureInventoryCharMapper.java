package com.adventure.core.service.mapper;

import com.adventure.core.domain.*;
import com.adventure.core.service.dto.AdventureInventoryCharDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureInventoryChar and its DTO AdventureInventoryCharDTO.
 */
@Mapper(componentModel = "spring", uses = {AdventureItemMapper.class})
public interface AdventureInventoryCharMapper extends EntityMapper<AdventureInventoryCharDTO, AdventureInventoryChar> {

    @Mapping(source = "adventureItem.id", target = "adventureItemId")
    AdventureInventoryCharDTO toDto(AdventureInventoryChar adventureInventoryChar);

    @Mapping(source = "adventureItemId", target = "adventureItem")
    AdventureInventoryChar toEntity(AdventureInventoryCharDTO adventureInventoryCharDTO);

    default AdventureInventoryChar fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureInventoryChar adventureInventoryChar = new AdventureInventoryChar();
        adventureInventoryChar.setId(id);
        return adventureInventoryChar;
    }
}
