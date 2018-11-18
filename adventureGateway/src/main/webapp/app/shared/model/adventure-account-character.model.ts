import { IAdventureSkill } from 'app/shared/model//adventure-skill.model';

export interface IAdventureAccountCharacter {
    id?: number;
    userId?: number;
    nickname?: string;
    gender?: boolean;
    adventureInventoryCharId?: number;
    adventureCharacteristicId?: number;
    adventureRaceId?: number;
    adventureSkills?: IAdventureSkill[];
}

export class AdventureAccountCharacter implements IAdventureAccountCharacter {
    constructor(
        public id?: number,
        public userId?: number,
        public nickname?: string,
        public gender?: boolean,
        public adventureInventoryCharId?: number,
        public adventureCharacteristicId?: number,
        public adventureRaceId?: number,
        public adventureSkills?: IAdventureSkill[]
    ) {
        this.gender = this.gender || false;
    }
}
