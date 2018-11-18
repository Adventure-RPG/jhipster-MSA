package com.adventure.uaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.adventure.uaa.domain.enumeration.AdventureDefenceArmorType;

import com.adventure.uaa.domain.enumeration.AdventureActiveWeaponAttackDamage;

/**
 * A AdventureAttributes.
 */
@Entity
@Table(name = "adventure_attributes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adventureattributes")
public class AdventureAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "defence", nullable = false)
    private Long defence;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "defence_armor_type", nullable = false)
    private AdventureDefenceArmorType defenceArmorType;

    @NotNull
    @Column(name = "fire_resistance", nullable = false)
    private Long fireResistance;

    @NotNull
    @Column(name = "earth_resistance", nullable = false)
    private Long earthResistance;

    @NotNull
    @Column(name = "water_resistance", nullable = false)
    private Long waterResistance;

    @NotNull
    @Column(name = "wind_resistance", nullable = false)
    private Long windResistance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "active_weapon_attack_damage", nullable = false)
    private AdventureActiveWeaponAttackDamage activeWeaponAttackDamage;

    @NotNull
    @Column(name = "active_weapon_attack_hit", nullable = false)
    private Long activeWeaponAttackHit;

    @NotNull
    @Column(name = "active_weapon_attack_type", nullable = false)
    private Long activeWeaponAttackType;

    @NotNull
    @Column(name = "jhi_size", nullable = false)
    private Long size;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDefence() {
        return defence;
    }

    public AdventureAttributes defence(Long defence) {
        this.defence = defence;
        return this;
    }

    public void setDefence(Long defence) {
        this.defence = defence;
    }

    public AdventureDefenceArmorType getDefenceArmorType() {
        return defenceArmorType;
    }

    public AdventureAttributes defenceArmorType(AdventureDefenceArmorType defenceArmorType) {
        this.defenceArmorType = defenceArmorType;
        return this;
    }

    public void setDefenceArmorType(AdventureDefenceArmorType defenceArmorType) {
        this.defenceArmorType = defenceArmorType;
    }

    public Long getFireResistance() {
        return fireResistance;
    }

    public AdventureAttributes fireResistance(Long fireResistance) {
        this.fireResistance = fireResistance;
        return this;
    }

    public void setFireResistance(Long fireResistance) {
        this.fireResistance = fireResistance;
    }

    public Long getEarthResistance() {
        return earthResistance;
    }

    public AdventureAttributes earthResistance(Long earthResistance) {
        this.earthResistance = earthResistance;
        return this;
    }

    public void setEarthResistance(Long earthResistance) {
        this.earthResistance = earthResistance;
    }

    public Long getWaterResistance() {
        return waterResistance;
    }

    public AdventureAttributes waterResistance(Long waterResistance) {
        this.waterResistance = waterResistance;
        return this;
    }

    public void setWaterResistance(Long waterResistance) {
        this.waterResistance = waterResistance;
    }

    public Long getWindResistance() {
        return windResistance;
    }

    public AdventureAttributes windResistance(Long windResistance) {
        this.windResistance = windResistance;
        return this;
    }

    public void setWindResistance(Long windResistance) {
        this.windResistance = windResistance;
    }

    public AdventureActiveWeaponAttackDamage getActiveWeaponAttackDamage() {
        return activeWeaponAttackDamage;
    }

    public AdventureAttributes activeWeaponAttackDamage(AdventureActiveWeaponAttackDamage activeWeaponAttackDamage) {
        this.activeWeaponAttackDamage = activeWeaponAttackDamage;
        return this;
    }

    public void setActiveWeaponAttackDamage(AdventureActiveWeaponAttackDamage activeWeaponAttackDamage) {
        this.activeWeaponAttackDamage = activeWeaponAttackDamage;
    }

    public Long getActiveWeaponAttackHit() {
        return activeWeaponAttackHit;
    }

    public AdventureAttributes activeWeaponAttackHit(Long activeWeaponAttackHit) {
        this.activeWeaponAttackHit = activeWeaponAttackHit;
        return this;
    }

    public void setActiveWeaponAttackHit(Long activeWeaponAttackHit) {
        this.activeWeaponAttackHit = activeWeaponAttackHit;
    }

    public Long getActiveWeaponAttackType() {
        return activeWeaponAttackType;
    }

    public AdventureAttributes activeWeaponAttackType(Long activeWeaponAttackType) {
        this.activeWeaponAttackType = activeWeaponAttackType;
        return this;
    }

    public void setActiveWeaponAttackType(Long activeWeaponAttackType) {
        this.activeWeaponAttackType = activeWeaponAttackType;
    }

    public Long getSize() {
        return size;
    }

    public AdventureAttributes size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdventureAttributes adventureAttributes = (AdventureAttributes) o;
        if (adventureAttributes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureAttributes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureAttributes{" +
            "id=" + getId() +
            ", defence=" + getDefence() +
            ", defenceArmorType='" + getDefenceArmorType() + "'" +
            ", fireResistance=" + getFireResistance() +
            ", earthResistance=" + getEarthResistance() +
            ", waterResistance=" + getWaterResistance() +
            ", windResistance=" + getWindResistance() +
            ", activeWeaponAttackDamage='" + getActiveWeaponAttackDamage() + "'" +
            ", activeWeaponAttackHit=" + getActiveWeaponAttackHit() +
            ", activeWeaponAttackType=" + getActiveWeaponAttackType() +
            ", size=" + getSize() +
            "}";
    }
}
