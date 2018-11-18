package com.adventure.uaa.repository;

import com.adventure.uaa.domain.AdventureCharacteristic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdventureCharacteristic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdventureCharacteristicRepository extends JpaRepository<AdventureCharacteristic, Long> {

}
