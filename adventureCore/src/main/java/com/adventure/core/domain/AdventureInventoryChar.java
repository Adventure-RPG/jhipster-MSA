package com.adventure.core.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdventureInventoryChar.
 */
@Document(collection = "adventure_inventory_char")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adventureinventorychar")
public class AdventureInventoryChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @DBRef
    @Field("adventureItem")
    @JsonIgnoreProperties("adventureInventoryChars")
    private AdventureItem adventureItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (!(o instanceof AdventureInventoryChar)) {
            return false;
        }
        return id != null && id.equals(((AdventureInventoryChar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdventureInventoryChar{" +
            "id=" + getId() +
            "}";
    }
}
