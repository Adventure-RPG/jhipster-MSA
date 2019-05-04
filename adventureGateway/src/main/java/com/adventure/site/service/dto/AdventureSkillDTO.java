package com.adventure.site.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.adventure.site.domain.AdventureSkill} entity.
 */
public class AdventureSkillDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private Boolean position;


    private String adventureScriptId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAdventureScriptId() {
        return adventureScriptId;
    }

    public void setAdventureScriptId(String adventureScriptId) {
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
