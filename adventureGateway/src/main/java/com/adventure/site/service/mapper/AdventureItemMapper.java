package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureItem and its DTO AdventureItemDTO.
 */
@Mapper(componentModel = "spring", uses = {AdventureAttributesMapper.class})
public interface AdventureItemMapper extends EntityMapper<AdventureItemDTO, AdventureItem> {

    @Mapping(source = "adventureAttributes.id", target = "adventureAttributesId")
    AdventureItemDTO toDto(AdventureItem adventureItem);

    @Mapping(source = "adventureAttributesId", target = "adventureAttributes")
    AdventureItem toEntity(AdventureItemDTO adventureItemDTO);

    default AdventureItem fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureItem adventureItem = new AdventureItem();
        adventureItem.setId(id);
        return adventureItem;
    }
}
