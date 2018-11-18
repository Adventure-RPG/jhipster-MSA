package com.adventure.core.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AdventureInventoryChar entity.
 */
public class AdventureInventoryCharDTO implements Serializable {

    private String id;

    private String adventureItemId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdventureItemId() {
        return adventureItemId;
    }

    public void setAdventureItemId(String adventureItemId) {
        this.adventureItemId = adventureItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdventureInventoryCharDTO adventureInventoryCharDTO = (AdventureInventoryCharDTO) o;
        if (adventureInventoryCharDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureInventoryCharDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureInventoryCharDTO{" +
            "id=" + getId() +
            ", adventureItem=" + getAdventureItemId() +
            "}";
    }
}
