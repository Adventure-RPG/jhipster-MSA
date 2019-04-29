package com.adventure.core.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdventureModel.
 */
@Document(collection = "adventure_model")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adventuremodel")
public class AdventureModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("file")
    private byte[] file;

    @Field("file_content_type")
    private String fileContentType;

    @DBRef
    @Field("adventureCategoryType")
    @JsonIgnoreProperties("adventureModels")
    private AdventureCategoryType adventureCategoryType;

    @DBRef
    @Field("adventureRaceOptions")
    @JsonIgnore
    private Set<AdventureRaceOptions> adventureRaceOptions = new HashSet<>();

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
