package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureFractionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdventureFraction} and its DTO {@link AdventureFractionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureFractionMapper extends EntityMapper<AdventureFractionDTO, AdventureFraction> {


    @Mapping(target = "adventureRaces", ignore = true)
    AdventureFraction toEntity(AdventureFractionDTO adventureFractionDTO);

    default AdventureFraction fromId(String id) {
        if (id == null) {
            return null;
        }
        AdventureFraction adventureFraction = new AdventureFraction();
        adventureFraction.setId(id);
        return adventureFraction;
    }
}
