package com.adventure.uaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.adventure.uaa.domain.enumeration.AdventureEquipmentSlot;

/**
 * A AdventureItem.
 */
@Entity
@Table(name = "adventure_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adventureitem")
public class AdventureItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_equipment")
    private Boolean isEquipment;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_slot")
    private AdventureEquipmentSlot equipmentSlot;

    @Column(name = "price")
    private Long price;

    @Column(name = "weight")
    private Long weight;

    @OneToOne    @JoinColumn(unique = true)
    private AdventureAttributes adventureAttributes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsEquipment() {
        return isEquipment;
    }

    public AdventureItem isEquipment(Boolean isEquipment) {
        this.isEquipment = isEquipment;
        return this;
    }

    public void setIsEquipment(Boolean isEquipment) {
        this.isEquipment = isEquipment;
    }

    public AdventureEquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public AdventureItem equipmentSlot(AdventureEquipmentSlot equipmentSlot) {
        this.equipmentSlot = equipmentSlot;
        return this;
    }

    public void setEquipmentSlot(AdventureEquipmentSlot equipmentSlot) {
        this.equipmentSlot = equipmentSlot;
    }

    public Long getPrice() {
        return price;
    }

    public AdventureItem price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getWeight() {
        return weight;
    }

    public AdventureItem weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public AdventureAttributes getAdventureAttributes() {
        return adventureAttributes;
    }

    public AdventureItem adventureAttributes(AdventureAttributes adventureAttributes) {
        this.adventureAttributes = adventureAttributes;
        return this;
    }

    public void setAdventureAttributes(AdventureAttributes adventureAttributes) {
        this.adventureAttributes = adventureAttributes;
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
        AdventureItem adventureItem = (AdventureItem) o;
        if (adventureItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureItem{" +
            "id=" + getId() +
            ", isEquipment='" + isIsEquipment() + "'" +
            ", equipmentSlot='" + getEquipmentSlot() + "'" +
            ", price=" + getPrice() +
            ", weight=" + getWeight() +
            "}";
    }
}
