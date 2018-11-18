package com.adventure.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdventureAccountCharacter.
 */
@Document(indexName = "adventureaccountcharacter")
public class AdventureAccountCharacter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    private Integer userId;

    @NotNull
    private String nickname;

    @NotNull
    private Boolean gender;

    private AdventureInventoryChar adventureInventoryChar;

    private AdventureCharacteristic adventureCharacteristic;

    @JsonIgnoreProperties("")
    private AdventureRace adventureRace;

    private Set<AdventureSkill> adventureSkills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public  getId() {
        return id;
    }

    public void setId( id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public AdventureAccountCharacter userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public AdventureAccountCharacter nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean isGender() {
        return gender;
    }

    public AdventureAccountCharacter gender(Boolean gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public AdventureInventoryChar getAdventureInventoryChar() {
        return adventureInventoryChar;
    }

    public AdventureAccountCharacter adventureInventoryChar(AdventureInventoryChar adventureInventoryChar) {
        this.adventureInventoryChar = adventureInventoryChar;
        return this;
    }

    public void setAdventureInventoryChar(AdventureInventoryChar adventureInventoryChar) {
        this.adventureInventoryChar = adventureInventoryChar;
    }

    public AdventureCharacteristic getAdventureCharacteristic() {
        return adventureCharacteristic;
    }

    public AdventureAccountCharacter adventureCharacteristic(AdventureCharacteristic adventureCharacteristic) {
        this.adventureCharacteristic = adventureCharacteristic;
        return this;
    }

    public void setAdventureCharacteristic(AdventureCharacteristic adventureCharacteristic) {
        this.adventureCharacteristic = adventureCharacteristic;
    }

    public AdventureRace getAdventureRace() {
        return adventureRace;
    }

    public AdventureAccountCharacter adventureRace(AdventureRace adventureRace) {
        this.adventureRace = adventureRace;
        return this;
    }

    public void setAdventureRace(AdventureRace adventureRace) {
        this.adventureRace = adventureRace;
    }

    public Set<AdventureSkill> getAdventureSkills() {
        return adventureSkills;
    }

    public AdventureAccountCharacter adventureSkills(Set<AdventureSkill> adventureSkills) {
        this.adventureSkills = adventureSkills;
        return this;
    }

    public AdventureAccountCharacter addAdventureSkill(AdventureSkill adventureSkill) {
        this.adventureSkills.add(adventureSkill);
        return this;
    }

    public AdventureAccountCharacter removeAdventureSkill(AdventureSkill adventureSkill) {
        this.adventureSkills.remove(adventureSkill);
        return this;
    }

    public void setAdventureSkills(Set<AdventureSkill> adventureSkills) {
        this.adventureSkills = adventureSkills;
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
        AdventureAccountCharacter adventureAccountCharacter = (AdventureAccountCharacter) o;
        if (adventureAccountCharacter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureAccountCharacter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureAccountCharacter{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", nickname='" + getNickname() + "'" +
            ", gender='" + isGender() + "'" +
            "}";
    }
}
