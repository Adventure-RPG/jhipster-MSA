package com.adventure.site.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A AdventureRaceOptions.
 */
@Document(collection = "adventure_race_options")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adventureraceoptions")
public class AdventureRaceOptions implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @NotNull
    @Min(value = 1)
    @Field("height")
    private Integer height;

    @NotNull
    @Min(value = 1)
    @Field("weight")
    private Integer weight;

    @DBRef
    @Field("adventureModelOptions")
    @JsonIgnoreProperties("adventureRaceOptions")
    private AdventureModelOptions adventureModelOptions;

    @DBRef
    @Field("adventureModels")
    private Set<AdventureModel> adventureModels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHeight() {
        return height;
    }

    public AdventureRaceOptions height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public AdventureRaceOptions weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public AdventureModelOptions getAdventureModelOptions() {
        return adventureModelOptions;
    }

    public AdventureRaceOptions adventureModelOptions(AdventureModelOptions adventureModelOptions) {
        this.adventureModelOptions = adventureModelOptions;
        return this;
    }

    public void setAdventureModelOptions(AdventureModelOptions adventureModelOptions) {
        this.adventureModelOptions = adventureModelOptions;
    }

    public Set<AdventureModel> getAdventureModels() {
        return adventureModels;
    }

    public AdventureRaceOptions adventureModels(Set<AdventureModel> adventureModels) {
        this.adventureModels = adventureModels;
        return this;
    }

    public AdventureRaceOptions addAdventureModel(AdventureModel adventureModel) {
        this.adventureModels.add(adventureModel);
        adventureModel.getAdventureRaceOptions().add(this);
        return this;
    }

    public AdventureRaceOptions removeAdventureModel(AdventureModel adventureModel) {
        this.adventureModels.remove(adventureModel);
        adventureModel.getAdventureRaceOptions().remove(this);
        return this;
    }

    public void setAdventureModels(Set<AdventureModel> adventureModels) {
        this.adventureModels = adventureModels;
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
        AdventureRaceOptions adventureRaceOptions = (AdventureRaceOptions) o;
        if (adventureRaceOptions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureRaceOptions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureRaceOptions{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            "}";
    }
}
