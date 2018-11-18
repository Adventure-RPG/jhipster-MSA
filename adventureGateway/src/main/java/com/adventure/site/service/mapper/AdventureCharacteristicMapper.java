package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureCharacteristicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureCharacteristic and its DTO AdventureCharacteristicDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureCharacteristicMapper extends EntityMapper<AdventureCharacteristicDTO, AdventureCharacteristic> {


}
