package com.adventure.uaa.domain;


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
 * A AdventureAccountCharacter.
 */
@Entity
@Table(name = "adventure_account_character")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adventureaccountcharacter")
public class AdventureAccountCharacter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @NotNull
    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @OneToOne
    @JoinColumn(unique = true)
    private AdventureInventoryChar adventureInventoryChar;

    @OneToOne
    @JoinColumn(unique = true)
    private AdventureCharacteristic adventureCharacteristic;

    @ManyToOne
    @JsonIgnoreProperties("adventureAccountCharacters")
    private AdventureRace adventureRace;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "adventure_account_character_adventure_skill",
               joinColumns = @JoinColumn(name = "adventure_account_character_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "adventure_skill_id", referencedColumnName = "id"))
    private Set<AdventureSkill> adventureSkills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public AdventureAccountCharacter nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean isGender() {
        return gender;
    }

    public AdventureAccountCharacter gender(Boolean gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public AdventureInventoryChar getAdventureInventoryChar() {
        return adventureInventoryChar;
    }

    public AdventureAccountCharacter adventureInventoryChar(AdventureInventoryChar adventureInventoryChar) {
        this.adventureInventoryChar = adventureInventoryChar;
        return this;
    }

    public void setAdventureInventoryChar(AdventureInventoryChar adventureInventoryChar) {
        this.adventureInventoryChar = adventureInventoryChar;
    }

    public AdventureCharacteristic getAdventureCharacteristic() {
        return adventureCharacteristic;
    }

    public AdventureAccountCharacter adventureCharacteristic(AdventureCharacteristic adventureCharacteristic) {
        this.adventureCharacteristic = adventureCharacteristic;
        return this;
    }

    public void setAdventureCharacteristic(AdventureCharacteristic adventureCharacteristic) {
        this.adventureCharacteristic = adventureCharacteristic;
    }

    public AdventureRace getAdventureRace() {
        return adventureRace;
    }

    public AdventureAccountCharacter adventureRace(AdventureRace adventureRace) {
        this.adventureRace = adventureRace;
        return this;
    }

    public void setAdventureRace(AdventureRace adventureRace) {
        this.adventureRace = adventureRace;
    }

    public Set<AdventureSkill> getAdventureSkills() {
        return adventureSkills;
    }

    public AdventureAccountCharacter adventureSkills(Set<AdventureSkill> adventureSkills) {
        this.adventureSkills = adventureSkills;
        return this;
    }

    public AdventureAccountCharacter addAdventureSkill(AdventureSkill adventureSkill) {
        this.adventureSkills.add(adventureSkill);
        adventureSkill.getAdventureAccountCharacters().add(this);
        return this;
    }

    public AdventureAccountCharacter removeAdventureSkill(AdventureSkill adventureSkill) {
        this.adventureSkills.remove(adventureSkill);
        adventureSkill.getAdventureAccountCharacters().remove(this);
        return this;
    }

    public void setAdventureSkills(Set<AdventureSkill> adventureSkills) {
        this.adventureSkills = adventureSkills;
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
        AdventureAccountCharacter adventureAccountCharacter = (AdventureAccountCharacter) o;
        if (adventureAccountCharacter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureAccountCharacter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureAccountCharacter{" +
            "id=" + getId() +
            ", nickname='" + getNickname() + "'" +
            ", gender='" + isGender() + "'" +
            "}";
    }
}
