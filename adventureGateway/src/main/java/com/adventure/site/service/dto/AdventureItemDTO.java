package com.adventure.site.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.adventure.site.domain.enumeration.AdventureEquipmentSlot;

/**
 * A DTO for the {@link com.adventure.site.domain.AdventureItem} entity.
 */
public class AdventureItemDTO implements Serializable {

    private String id;

    private Boolean isEquipment;

    private AdventureEquipmentSlot equipmentSlot;

    private Long price;

    private Long weight;


    private String adventureAttributesId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isIsEquipment() {
        return isEquipment;
    }

    public void setIsEquipment(Boolean isEquipment) {
        this.isEquipment = isEquipment;
    }

    public AdventureEquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public void setEquipmentSlot(AdventureEquipmentSlot equipmentSlot) {
        this.equipmentSlot = equipmentSlot;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getAdventureAttributesId() {
        return adventureAttributesId;
    }

    public void setAdventureAttributesId(String adventureAttributesId) {
        this.adventureAttributesId = adventureAttributesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdventureItemDTO adventureItemDTO = (AdventureItemDTO) o;
        if (adventureItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adventureItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdventureItemDTO{" +
            "id=" + getId() +
            ", isEquipment='" + isIsEquipment() + "'" +
            ", equipmentSlot='" + getEquipmentSlot() + "'" +
            ", price=" + getPrice() +
            ", weight=" + getWeight() +
            ", adventureAttributes=" + getAdventureAttributesId() +
            "}";
    }
}
