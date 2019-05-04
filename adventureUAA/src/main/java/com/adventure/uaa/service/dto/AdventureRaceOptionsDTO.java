package com.adventure.uaa.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.adventure.uaa.domain.AdventureRaceOptions} entity.
 */
public class AdventureRaceOptionsDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    private Integer height;

    @NotNull
    @Min(value = 1)
    private Integer weight;


    private Long adventureModelOptionsId;

    private Set<AdventureModelDTO> adventureModels = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getAdventureModelOptionsId() {
        return adventureModelOptionsId;
    }

    public void setAdventureModelOptionsId(Long adventureModelOptionsId) {
        this.adventureModelOptionsId = adventureModelOptionsId;
    }

    public Set<AdventureModelDTO> getAdventureModels() {
        return adventureModels;
    }

    public void setAdventureModels(Set<AdventureModelDTO> adventureModels) {
        this.adventureModels = adventureModels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdventureRaceOptionsDTO adventureRaceOptionsDTO = (AdventureRaceOptionsDTO) o;
        if (adventureRaceOptionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureRaceOptionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureRaceOptionsDTO{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", adventureModelOptions=" + getAdventureModelOptionsId() +
            "}";
    }
}
