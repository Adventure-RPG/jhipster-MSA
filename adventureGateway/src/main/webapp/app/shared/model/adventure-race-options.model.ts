import { IAdventureModel } from 'app/shared/model/adventure-model.model';

export interface IAdventureRaceOptions {
  id?: string;
  height?: number;
  weight?: number;
  adventureModelOptionsId?: string;
  adventureModels?: IAdventureModel[];
}

export class AdventureRaceOptions implements IAdventureRaceOptions {
  constructor(
    public id?: string,
    public height?: number,
    public weight?: number,
    public adventureModelOptionsId?: string,
    public adventureModels?: IAdventureModel[]
  ) {}
}
