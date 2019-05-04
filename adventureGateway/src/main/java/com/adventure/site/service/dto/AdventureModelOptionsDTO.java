package com.adventure.site.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.adventure.site.domain.AdventureModelOptions} entity.
 */
public class AdventureModelOptionsDTO implements Serializable {

    private String id;

    private String color;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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
