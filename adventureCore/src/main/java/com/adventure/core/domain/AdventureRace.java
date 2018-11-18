package com.adventure.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdventureRace.
 */
@Document(collection = "adventure_race")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adventurerace")
public class AdventureRace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("desc")
    private String desc;

    
    @Field("image")
    private byte[] image;

    @Field("image_content_type")
    private String imageContentType;

    @DBRef
    @Field("adventureRaceOptions")
    @JsonIgnoreProperties("")
    private AdventureRaceOptions adventureRaceOptions;

    @DBRef
    @Field("adventureFractions")
    private Set<AdventureFraction> adventureFractions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
