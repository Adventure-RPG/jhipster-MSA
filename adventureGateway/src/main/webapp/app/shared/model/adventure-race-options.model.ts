import { IAdventureModel } from 'app/shared/model//adventure-model.model';

export interface IAdventureRaceOptions {
    id?: number;
    height?: number;
    weight?: number;
    adventureModelOptionsId?: number;
    adventureModels?: IAdventureModel[];
}

export class AdventureRaceOptions implements IAdventureRaceOptions {
    constructor(
        public id?: number,
        public height?: number,
        public weight?: number,
        public adventureModelOptionsId?: number,
        public adventureModels?: IAdventureModel[]
    ) {}
}
