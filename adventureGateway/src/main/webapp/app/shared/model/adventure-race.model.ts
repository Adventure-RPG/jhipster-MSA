import { IAdventureFraction } from 'app/shared/model//adventure-fraction.model';

export interface IAdventureRace {
    id?: number;
    name?: string;
    desc?: string;
    imageContentType?: string;
    image?: any;
    adventureRaceOptionsId?: number;
    adventureFractions?: IAdventureFraction[];
}

export class AdventureRace implements IAdventureRace {
    constructor(
        public id?: number,
        public name?: string,
        public desc?: string,
        public imageContentType?: string,
        public image?: any,
        public adventureRaceOptionsId?: number,
        public adventureFractions?: IAdventureFraction[]
    ) {}
}
