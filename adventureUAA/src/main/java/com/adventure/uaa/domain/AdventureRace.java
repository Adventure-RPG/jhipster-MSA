package com.adventure.uaa.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdventureRace.
 */
@Entity
@Table(name = "adventure_race")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adventurerace")
public class AdventureRace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_desc", nullable = false)
    private String desc;

    
    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @ManyToOne
    @JsonIgnoreProperties("adventureRaces")
    private AdventureRaceOptions adventureRaceOptions;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "adventure_race_adventure_fraction",
               joinColumns = @JoinColumn(name = "adventure_race_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "adventure_fraction_id", referencedColumnName = "id"))
    private Set<AdventureFraction> adventureFractions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        adventureFraction.getAdventureRaces().add(this);
        return this;
    }

    public AdventureRace removeAdventureFraction(AdventureFraction adventureFraction) {
        this.adventureFractions.remove(adventureFraction);
        adventureFraction.getAdventureRaces().remove(this);
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
        if (!(o instanceof AdventureRace)) {
            return false;
        }
        return id != null && id.equals(((AdventureRace) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
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
