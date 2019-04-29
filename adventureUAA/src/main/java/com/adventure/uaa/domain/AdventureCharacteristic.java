package com.adventure.uaa.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdventureCharacteristic.
 */
@Entity
@Table(name = "adventure_characteristic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adventurecharacteristic")
public class AdventureCharacteristic implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    @Column(name = "strength", nullable = false)
    private Integer strength;

    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    @Column(name = "agility", nullable = false)
    private Integer agility;

    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    @Column(name = "vitality", nullable = false)
    private Integer vitality;

    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    @Column(name = "luck", nullable = false)
    private Integer luck;

    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    @Column(name = "intelligence", nullable = false)
    private Integer intelligence;

    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    @Column(name = "wisdom", nullable = false)
    private Integer wisdom;

    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    @Column(name = "charisma", nullable = false)
    private Integer charisma;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStrength() {
        return strength;
    }

    public AdventureCharacteristic strength(Integer strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getAgility() {
        return agility;
    }

    public AdventureCharacteristic agility(Integer agility) {
        this.agility = agility;
        return this;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getVitality() {
        return vitality;
    }

    public AdventureCharacteristic vitality(Integer vitality) {
        this.vitality = vitality;
        return this;
    }

    public void setVitality(Integer vitality) {
        this.vitality = vitality;
    }

    public Integer getLuck() {
        return luck;
    }

    public AdventureCharacteristic luck(Integer luck) {
        this.luck = luck;
        return this;
    }

    public void setLuck(Integer luck) {
        this.luck = luck;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public AdventureCharacteristic intelligence(Integer intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getWisdom() {
        return wisdom;
    }

    public AdventureCharacteristic wisdom(Integer wisdom) {
        this.wisdom = wisdom;
        return this;
    }

    public void setWisdom(Integer wisdom) {
        this.wisdom = wisdom;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public AdventureCharacteristic charisma(Integer charisma) {
        this.charisma = charisma;
        return this;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
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
        AdventureCharacteristic adventureCharacteristic = (AdventureCharacteristic) o;
        if (adventureCharacteristic.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureCharacteristic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureCharacteristic{" +
            "id=" + getId() +
            ", strength=" + getStrength() +
            ", agility=" + getAgility() +
            ", vitality=" + getVitality() +
            ", luck=" + getLuck() +
            ", intelligence=" + getIntelligence() +
            ", wisdom=" + getWisdom() +
            ", charisma=" + getCharisma() +
            "}";
    }
}
