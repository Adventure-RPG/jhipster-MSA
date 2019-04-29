export const enum AdventureEquipmentSlot {
    WEAPON1 = 'WEAPON1',
    WEAPON2 = 'WEAPON2',
    ARMOR = 'ARMOR',
    HELMET = 'HELMET',
    GLOVES = 'GLOVES',
    BOOTS = 'BOOTS',
    BELT = 'BELT',
    NECKLACE = 'NECKLACE',
    RING1 = 'RING1',
    RING2 = 'RING2',
    CLOAK = 'CLOAK',
    BELT_SLOT_1 = 'BELT_SLOT_1',
    BELT_SLOT_2 = 'BELT_SLOT_2',
    BELT_SLOT_3 = 'BELT_SLOT_3',
    BELT_SLOT_4 = 'BELT_SLOT_4',
    BELT_SLOT_5 = 'BELT_SLOT_5',
    UNEQUIPMENT_ITEM = 'UNEQUIPMENT_ITEM',
    QUEST_ITEM = 'QUEST_ITEM'
}

export interface IAdventureItem {
    id?: string;
    isEquipment?: boolean;
    equipmentSlot?: AdventureEquipmentSlot;
    price?: number;
    weight?: number;
    adventureAttributesId?: string;
}

export class AdventureItem implements IAdventureItem {
    constructor(
        public id?: string,
        public isEquipment?: boolean,
        public equipmentSlot?: AdventureEquipmentSlot,
        public price?: number,
        public weight?: number,
        public adventureAttributesId?: string
    ) {
        this.isEquipment = this.isEquipment || false;
    }
}
