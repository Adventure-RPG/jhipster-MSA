import { IAdventureRace } from 'app/shared/model/adventure-race.model';

export interface IAdventureFraction {
    id?: string;
    name?: string;
    desc?: string;
    imageContentType?: string;
    image?: any;
    adventureRaces?: IAdventureRace[];
}

export class AdventureFraction implements IAdventureFraction {
    constructor(
        public id?: string,
        public name?: string,
        public desc?: string,
        public imageContentType?: string,
        public image?: any,
        public adventureRaces?: IAdventureRace[]
    ) {}
}
