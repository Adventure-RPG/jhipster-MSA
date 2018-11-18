package com.adventure.site.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdventureScript.
 */
@Document(indexName = "adventurescript")
public class AdventureScript implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    private String name;

    private byte[] file;

    private String fileContentType;

    private String argumentsScript;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public  getId() {
        return id;
    }

    public void setId( id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AdventureScript name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public AdventureScript file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public AdventureScript fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getArgumentsScript() {
        return argumentsScript;
    }

    public AdventureScript argumentsScript(String argumentsScript) {
        this.argumentsScript = argumentsScript;
        return this;
    }

    public void setArgumentsScript(String argumentsScript) {
        this.argumentsScript = argumentsScript;
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
        AdventureScript adventureScript = (AdventureScript) o;
        if (adventureScript.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureScript.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureScript{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", argumentsScript='" + getArgumentsScript() + "'" +
            "}";
    }
}
