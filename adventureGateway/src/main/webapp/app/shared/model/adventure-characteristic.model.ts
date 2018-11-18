export interface IAdventureCharacteristic {
    id?: number;
    strength?: number;
    agility?: number;
    vitality?: number;
    luck?: number;
    intelligence?: number;
    wisdom?: number;
    charisma?: number;
}

export class AdventureCharacteristic implements IAdventureCharacteristic {
    constructor(
        public id?: number,
        public strength?: number,
        public agility?: number,
        public vitality?: number,
        public luck?: number,
        public intelligence?: number,
        public wisdom?: number,
        public charisma?: number
    ) {}
}
