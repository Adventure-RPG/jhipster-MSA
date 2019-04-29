package com.adventure.site.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AdventureModelOptions.
 */
@Document(collection = "adventure_model_options")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adventuremodeloptions")
public class AdventureModelOptions implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("color")
    private String color;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public AdventureModelOptions color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
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
        AdventureModelOptions adventureModelOptions = (AdventureModelOptions) o;
        if (adventureModelOptions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureModelOptions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureModelOptions{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            "}";
    }
}
