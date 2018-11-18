package com.adventure.site.service.mapper;

import com.adventure.site.domain.*;
import com.adventure.site.service.dto.AdventureFractionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdventureFraction and its DTO AdventureFractionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdventureFractionMapper extends EntityMapper<AdventureFractionDTO, AdventureFraction> {


}
