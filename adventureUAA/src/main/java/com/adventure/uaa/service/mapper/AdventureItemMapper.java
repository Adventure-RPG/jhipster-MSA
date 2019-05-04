package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureItem} and its DTO {@link AdventureItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdventureAttributesMapper.class})
public interface AdventureItemMapper extends EntityMapper<AdventureItemDTO, AdventureItem> {

    @Mapping(source = "adventureAttributes.id", target = "adventureAttributesId")
    AdventureItemDTO toDto(AdventureItem adventureItem);

    @Mapping(source = "adventureAttributesId", target = "adventureAttributes")
    AdventureItem toEntity(AdventureItemDTO adventureItemDTO);

    default AdventureItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureItem adventureItem = new AdventureItem();
        adventureItem.setId(id);
        return adventureItem;
    }
}
