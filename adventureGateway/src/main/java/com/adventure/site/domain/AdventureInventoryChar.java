package com.adventure.site.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdventureInventoryChar.
 */
@Document(indexName = "adventureinventorychar")
public class AdventureInventoryChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @JsonIgnoreProperties("")
    private AdventureItem adventureItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public  getId() {
        return id;
    }

    public void setId( id) {
        this.id = id;
    }

    public AdventureItem getAdventureItem() {
        return adventureItem;
    }

    public AdventureInventoryChar adventureItem(AdventureItem adventureItem) {
        this.adventureItem = adventureItem;
        return this;
    }

    public void setAdventureItem(AdventureItem adventureItem) {
        this.adventureItem = adventureItem;
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
        AdventureInventoryChar adventureInventoryChar = (AdventureInventoryChar) o;
        if (adventureInventoryChar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureInventoryChar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureInventoryChar{" +
            "id=" + getId() +
            "}";
    }
}
