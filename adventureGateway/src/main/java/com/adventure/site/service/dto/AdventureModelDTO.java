package com.adventure.site.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AdventureModel entity.
 */
public class AdventureModelDTO implements Serializable {

    private String id;

    private String name;

    private byte[] file;

    private String fileContentType;

    private String adventureCategoryTypeId;

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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getAdventureCategoryTypeId() {
        return adventureCategoryTypeId;
    }

    public void setAdventureCategoryTypeId(String adventureCategoryTypeId) {
        this.adventureCategoryTypeId = adventureCategoryTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdventureModelDTO adventureModelDTO = (AdventureModelDTO) o;
        if (adventureModelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureModelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureModelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", file='" + getFile() + "'" +
            ", adventureCategoryType=" + getAdventureCategoryTypeId() +
            "}";
    }
}
