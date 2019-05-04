package com.adventure.uaa.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdventureInventoryChar.
 */
@Entity
@Table(name = "adventure_inventory_char")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adventureinventorychar")
public class AdventureInventoryChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("adventureInventoryChars")
    private AdventureItem adventureItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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