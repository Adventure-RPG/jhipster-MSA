package com.adventure.site.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.adventure.site.domain.enumeration.AdventureDefenceArmorType;

import com.adventure.site.domain.enumeration.AdventureActiveWeaponAttackDamage;

/**
 * A AdventureAttributes.
 */
@Document(indexName = "adventureattributes")
public class AdventureAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    private Long defence;

    @NotNull
    private AdventureDefenceArmorType defenceArmorType;

    @NotNull
    private Long fireResistance;

    @NotNull
    private Long earthResistance;

    @NotNull
    private Long waterResistance;

    @NotNull
    private Long windResistance;

    @NotNull
    private AdventureActiveWeaponAttackDamage activeWeaponAttackDamage;

    @NotNull
    private Long activeWeaponAttackHit;

    @NotNull
    private Long activeWeaponAttackType;

    @NotNull
    private Long size;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public  getId() {
        return id;
    }

    public void setId( id) {
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
