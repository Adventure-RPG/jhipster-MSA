package com.adventure.uaa.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AdventureAccountCharacter entity.
 */
public class AdventureAccountCharacterDTO implements Serializable {

    private Long id;

    @NotNull
    private String nickname;

    @NotNull
    private Boolean gender;


    private Long adventureInventoryCharId;

    private Long adventureCharacteristicId;

    private Long adventureRaceId;

    private Set<AdventureSkillDTO> adventureSkills = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean isGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Long getAdventureInventoryCharId() {
        return adventureInventoryCharId;
    }

    public void setAdventureInventoryCharId(Long adventureInventoryCharId) {
        this.adventureInventoryCharId = adventureInventoryCharId;
    }

    public Long getAdventureCharacteristicId() {
        return adventureCharacteristicId;
    }

    public void setAdventureCharacteristicId(Long adventureCharacteristicId) {
        this.adventureCharacteristicId = adventureCharacteristicId;
    }

    public Long getAdventureRaceId() {
        return adventureRaceId;
    }

    public void setAdventureRaceId(Long adventureRaceId) {
        this.adventureRaceId = adventureRaceId;
    }

    public Set<AdventureSkillDTO> getAdventureSkills() {
        return adventureSkills;
    }

    public void setAdventureSkills(Set<AdventureSkillDTO> adventureSkills) {
        this.adventureSkills = adventureSkills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdventureAccountCharacterDTO adventureAccountCharacterDTO = (AdventureAccountCharacterDTO) o;
        if (adventureAccountCharacterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureAccountCharacterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureAccountCharacterDTO{" +
            "id=" + getId() +
            ", nickname='" + getNickname() + "'" +
            ", gender='" + isGender() + "'" +
            ", adventureInventoryChar=" + getAdventureInventoryCharId() +
            ", adventureCharacteristic=" + getAdventureCharacteristicId() +
            ", adventureRace=" + getAdventureRaceId() +
            "}";
    }
}
