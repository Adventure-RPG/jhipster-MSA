package com.adventure.uaa.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AdventureModelOptions entity.
 */
public class AdventureModelOptionsDTO implements Serializable {

    private Long id;

    private String color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdventureModelOptionsDTO adventureModelOptionsDTO = (AdventureModelOptionsDTO) o;
        if (adventureModelOptionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureModelOptionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureModelOptionsDTO{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            "}";
    }
}
