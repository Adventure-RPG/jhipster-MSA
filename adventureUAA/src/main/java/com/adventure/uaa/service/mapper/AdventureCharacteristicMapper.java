package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureCharacteristicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureCharacteristic and its DTO AdventureCharacteristicDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureCharacteristicMapper extends EntityMapper<AdventureCharacteristicDTO, AdventureCharacteristic> {



    default AdventureCharacteristic fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureCharacteristic adventureCharacteristic = new AdventureCharacteristic();
        adventureCharacteristic.setId(id);
        return adventureCharacteristic;
    }
}
