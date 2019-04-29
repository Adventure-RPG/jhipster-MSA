package com.adventure.uaa.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdventureSkill.
 */
@Entity
@Table(name = "adventure_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adventureskill")
public class AdventureSkill implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position")
    private Boolean position;

    @ManyToOne
    @JsonIgnoreProperties("adventureSkills")
    private AdventureScript adventureScript;

    @ManyToMany(mappedBy = "adventureSkills")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<AdventureAccountCharacter> adventureAccountCharacters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AdventureSkill name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPosition() {
        return position;
    }

    public AdventureSkill position(Boolean position) {
        this.position = position;
        return this;
    }

    public void setPosition(Boolean position) {
        this.position = position;
    }

    public AdventureScript getAdventureScript() {
        return adventureScript;
    }

    public AdventureSkill adventureScript(AdventureScript adventureScript) {
        this.adventureScript = adventureScript;
        return this;
    }

    public void setAdventureScript(AdventureScript adventureScript) {
        this.adventureScript = adventureScript;
    }

    public Set<AdventureAccountCharacter> getAdventureAccountCharacters() {
        return adventureAccountCharacters;
    }

    public AdventureSkill adventureAccountCharacters(Set<AdventureAccountCharacter> adventureAccountCharacters) {
        this.adventureAccountCharacters = adventureAccountCharacters;
        return this;
    }

    public AdventureSkill addAdventureAccountCharacter(AdventureAccountCharacter adventureAccountCharacter) {
        this.adventureAccountCharacters.add(adventureAccountCharacter);
        adventureAccountCharacter.getAdventureSkills().add(this);
        return this;
    }

    public AdventureSkill removeAdventureAccountCharacter(AdventureAccountCharacter adventureAccountCharacter) {
        this.adventureAccountCharacters.remove(adventureAccountCharacter);
        adventureAccountCharacter.getAdventureSkills().remove(this);
        return this;
    }

    public void setAdventureAccountCharacters(Set<AdventureAccountCharacter> adventureAccountCharacters) {
        this.adventureAccountCharacters = adventureAccountCharacters;
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
        AdventureSkill adventureSkill = (AdventureSkill) o;
        if (adventureSkill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureSkill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureSkill{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", position='" + isPosition() + "'" +
            "}";
    }
}
