package com.adventure.site.repository;

import com.adventure.site.domain.AdventureCharacteristic;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdventureCharacteristic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureCharacteristicRepository extends <AdventureCharacteristic, Long> {

}
