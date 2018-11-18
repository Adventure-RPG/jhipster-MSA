package com.adventure.site.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdventureModelOptions.
 */
@Document(indexName = "adventuremodeloptions")
public class AdventureModelOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String color;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public  getId() {
        return id;
    }

    public void setId( id) {
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
