package com.adventure.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdventureModel.
 */
@Document(indexName = "adventuremodel")
public class AdventureModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String name;

    private byte[] file;

    private String fileContentType;

    @JsonIgnoreProperties("")
    private AdventureCategoryType adventureCategoryType;

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
