package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureInventoryCharDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureInventoryChar} and its DTO {@link AdventureInventoryCharDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdventureItemMapper.class})
public interface AdventureInventoryCharMapper extends EntityMapper<AdventureInventoryCharDTO, AdventureInventoryChar> {

    @Mapping(source = "adventureItem.id", target = "adventureItemId")
    AdventureInventoryCharDTO toDto(AdventureInventoryChar adventureInventoryChar);

    @Mapping(source = "adventureItemId", target = "adventureItem")
    AdventureInventoryChar toEntity(AdventureInventoryCharDTO adventureInventoryCharDTO);

    default AdventureInventoryChar fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureInventoryChar adventureInventoryChar = new AdventureInventoryChar();
        adventureInventoryChar.setId(id);
        return adventureInventoryChar;
    }
}
