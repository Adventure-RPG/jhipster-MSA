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
 * A AdventureRace.
 */
@Document(indexName = "adventurerace")
public class AdventureRace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String desc;

    
    private byte[] image;

    private String imageContentType;

    @JsonIgnoreProperties("")
    private AdventureRaceOptions adventureRaceOptions;

    private Set<AdventureFraction> adventureFractions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public  getId() {
        return id;
    }

    public void setId( id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AdventureRace name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public AdventureRace desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getImage() {
        return image;
    }

    public AdventureRace image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public AdventureRace imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public AdventureRaceOptions getAdventureRaceOptions() {
        return adventureRaceOptions;
    }

    public AdventureRace adventureRaceOptions(AdventureRaceOptions adventureRaceOptions) {
        this.adventureRaceOptions = adventureRaceOptions;
        return this;
    }

    public void setAdventureRaceOptions(AdventureRaceOptions adventureRaceOptions) {
        this.adventureRaceOptions = adventureRaceOptions;
    }

    public Set<AdventureFraction> getAdventureFractions() {
        return adventureFractions;
    }

    public AdventureRace adventureFractions(Set<AdventureFraction> adventureFractions) {
        this.adventureFractions = adventureFractions;
        return this;
    }

    public AdventureRace addAdventureFraction(AdventureFraction adventureFraction) {
        this.adventureFractions.add(adventureFraction);
        return this;
    }

    public AdventureRace removeAdventureFraction(AdventureFraction adventureFraction) {
        this.adventureFractions.remove(adventureFraction);
        return this;
    }

    public void setAdventureFractions(Set<AdventureFraction> adventureFractions) {
        this.adventureFractions = adventureFractions;
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
        AdventureRace adventureRace = (AdventureRace) o;
        if (adventureRace.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureRace.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureRace{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
