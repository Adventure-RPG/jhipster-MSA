import { IAdventureFraction } from 'app/shared/model/adventure-fraction.model';

export interface IAdventureRace {
    id?: string;
    name?: string;
    desc?: string;
    imageContentType?: string;
    image?: any;
    adventureRaceOptionsId?: string;
    adventureFractions?: IAdventureFraction[];
}

export class AdventureRace implements IAdventureRace {
    constructor(
        public id?: string,
        public name?: string,
        public desc?: string,
        public imageContentType?: string,
        public image?: any,
        public adventureRaceOptionsId?: string,
        public adventureFractions?: IAdventureFraction[]
    ) {}
}
