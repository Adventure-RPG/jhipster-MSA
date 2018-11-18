package com.adventure.core.service.mapper;

import com.adventure.core.domain.*;
import com.adventure.core.service.dto.AdventureCharacteristicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureCharacteristic and its DTO AdventureCharacteristicDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureCharacteristicMapper extends EntityMapper<AdventureCharacteristicDTO, AdventureCharacteristic> {



    default AdventureCharacteristic fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureCharacteristic adventureCharacteristic = new AdventureCharacteristic();
        adventureCharacteristic.setId(id);
        return adventureCharacteristic;
    }
}
