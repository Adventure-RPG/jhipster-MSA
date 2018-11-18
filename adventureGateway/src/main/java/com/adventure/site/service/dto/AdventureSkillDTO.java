package com.adventure.site.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AdventureSkill entity.
 */
public class AdventureSkillDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Boolean position;

    private Long adventureScriptId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPosition() {
        return position;
    }

    public void setPosition(Boolean position) {
        this.position = position;
    }

    public Long getAdventureScriptId() {
        return adventureScriptId;
    }

    public void setAdventureScriptId(Long adventureScriptId) {
        this.adventureScriptId = adventureScriptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdventureSkillDTO adventureSkillDTO = (AdventureSkillDTO) o;
        if (adventureSkillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureSkillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureSkillDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", position='" + isPosition() + "'" +
            ", adventureScript=" + getAdventureScriptId() +
            "}";
    }
}
