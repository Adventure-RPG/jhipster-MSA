import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';

export interface IAdventureModel {
  id?: string;
  name?: string;
  fileContentType?: string;
  file?: any;
  adventureCategoryTypeId?: string;
  adventureRaceOptions?: IAdventureRaceOptions[];
}

export class AdventureModel implements IAdventureModel {
  constructor(
    public id?: string,
    public name?: string,
    public fileContentType?: string,
    public file?: any,
    public adventureCategoryTypeId?: string,
    public adventureRaceOptions?: IAdventureRaceOptions[]
  ) {}
}
