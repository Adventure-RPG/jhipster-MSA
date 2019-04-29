package com.adventure.uaa.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdventureModel.
 */
@Entity
@Table(name = "adventure_model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adventuremodel")
public class AdventureModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "jhi_file")
    private byte[] file;

    @Column(name = "jhi_file_content_type")
    private String fileContentType;

    @ManyToOne
    @JsonIgnoreProperties("adventureModels")
    private AdventureCategoryType adventureCategoryType;

    @ManyToMany(mappedBy = "adventureModels")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<AdventureRaceOptions> adventureRaceOptions = new HashSet<>();

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

    public AdventureModel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public AdventureModel file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public AdventureModel fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public AdventureCategoryType getAdventureCategoryType() {
        return adventureCategoryType;
    }

    public AdventureModel adventureCategoryType(AdventureCategoryType adventureCategoryType) {
        this.adventureCategoryType = adventureCategoryType;
        return this;
    }

    public void setAdventureCategoryType(AdventureCategoryType adventureCategoryType) {
        this.adventureCategoryType = adventureCategoryType;
    }

    public Set<AdventureRaceOptions> getAdventureRaceOptions() {
        return adventureRaceOptions;
    }

    public AdventureModel adventureRaceOptions(Set<AdventureRaceOptions> adventureRaceOptions) {
        this.adventureRaceOptions = adventureRaceOptions;
        return this;
    }

    public AdventureModel addAdventureRaceOptions(AdventureRaceOptions adventureRaceOptions) {
        this.adventureRaceOptions.add(adventureRaceOptions);
        adventureRaceOptions.getAdventureModels().add(this);
        return this;
    }

    public AdventureModel removeAdventureRaceOptions(AdventureRaceOptions adventureRaceOptions) {
        this.adventureRaceOptions.remove(adventureRaceOptions);
        adventureRaceOptions.getAdventureModels().remove(this);
        return this;
    }

    public void setAdventureRaceOptions(Set<AdventureRaceOptions> adventureRaceOptions) {
        this.adventureRaceOptions = adventureRaceOptions;
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
        AdventureModel adventureModel = (AdventureModel) o;
        if (adventureModel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureModel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
