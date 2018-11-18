package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureAccountCharacterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureAccountCharacter and its DTO AdventureAccountCharacterDTO.
 */
@Mapper(componentModel = "spring", uses = {AdventureInventoryCharMapper.class, AdventureCharacteristicMapper.class, AdventureRaceMapper.class, AdventureSkillMapper.class})
public interface AdventureAccountCharacterMapper extends EntityMapper<AdventureAccountCharacterDTO, AdventureAccountCharacter> {

    @Mapping(source = "adventureInventoryChar.id", target = "adventureInventoryCharId")
    @Mapping(source = "adventureCharacteristic.id", target = "adventureCharacteristicId")
    @Mapping(source = "adventureRace.id", target = "adventureRaceId")
    AdventureAccountCharacterDTO toDto(AdventureAccountCharacter adventureAccountCharacter);

    @Mapping(source = "adventureInventoryCharId", target = "adventureInventoryChar")
    @Mapping(source = "adventureCharacteristicId", target = "adventureCharacteristic")
    @Mapping(source = "adventureRaceId", target = "adventureRace")
    AdventureAccountCharacter toEntity(AdventureAccountCharacterDTO adventureAccountCharacterDTO);
}
