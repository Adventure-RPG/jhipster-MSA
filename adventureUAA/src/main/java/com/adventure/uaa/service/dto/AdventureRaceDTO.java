package com.adventure.uaa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the AdventureRace entity.
 */
public class AdventureRaceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String desc;

    
    @Lob
    private byte[] image;
    private String imageContentType;

    private Long adventureRaceOptionsId;

    private Set<AdventureFractionDTO> adventureFractions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getAdventureRaceOptionsId() {
        return adventureRaceOptionsId;
    }

    public void setAdventureRaceOptionsId(Long adventureRaceOptionsId) {
        this.adventureRaceOptionsId = adventureRaceOptionsId;
    }

    public Set<AdventureFractionDTO> getAdventureFractions() {
        return adventureFractions;
    }

    public void setAdventureFractions(Set<AdventureFractionDTO> adventureFractions) {
        this.adventureFractions = adventureFractions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdventureRaceDTO adventureRaceDTO = (AdventureRaceDTO) o;
        if (adventureRaceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureRaceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureRaceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", image='" + getImage() + "'" +
            ", adventureRaceOptions=" + getAdventureRaceOptionsId() +
            "}";
    }
}
