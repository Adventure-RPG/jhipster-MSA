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
import java.util.Objects;

/**
 * A AdventureSkill.
 */
@Document(collection = "adventure_skill")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adventureskill")
public class AdventureSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("position")
    private Boolean position;

    @DBRef
    @Field("adventureScript")
    @JsonIgnoreProperties("")
    private AdventureScript adventureScript;

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

    public AdventureSkill name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPosition() {
        return position;
    }

    public AdventureSkill position(Boolean position) {
        this.position = position;
        return this;
    }

    public void setPosition(Boolean position) {
        this.position = position;
    }

    public AdventureScript getAdventureScript() {
        return adventureScript;
    }

    public AdventureSkill adventureScript(AdventureScript adventureScript) {
        this.adventureScript = adventureScript;
        return this;
    }

    public void setAdventureScript(AdventureScript adventureScript) {
        this.adventureScript = adventureScript;
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
        AdventureSkill adventureSkill = (AdventureSkill) o;
        if (adventureSkill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureSkill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureSkill{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", position='" + isPosition() + "'" +
            "}";
    }
}
