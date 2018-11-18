package com.adventure.uaa.service.mapper;

import com.adventure.uaa.domain.*;
import com.adventure.uaa.service.dto.AdventureAccountCharacterDTO;

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

    default AdventureAccountCharacter fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdventureAccountCharacter adventureAccountCharacter = new AdventureAccountCharacter();
        adventureAccountCharacter.setId(id);
        return adventureAccountCharacter;
    }
}
