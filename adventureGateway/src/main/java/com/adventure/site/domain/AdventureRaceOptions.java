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
 * A AdventureRaceOptions.
 */
@Document(indexName = "adventureraceoptions")
public class AdventureRaceOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Min(value = 1)
    private Integer height;

    @NotNull
    @Min(value = 1)
    private Integer weight;

    @JsonIgnoreProperties("")
    private AdventureModelOptions adventureModelOptions;

    private Set<AdventureModel> adventureModels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public  getId() {
        return id;
    }

    public void setId( id) {
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
        return this;
    }

    public AdventureRaceOptions removeAdventureModel(AdventureModel adventureModel) {
        this.adventureModels.remove(adventureModel);
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
