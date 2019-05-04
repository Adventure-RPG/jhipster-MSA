import { IAdventureSkill } from 'app/shared/model/adventure-skill.model';

export interface IAdventureAccountCharacter {
  id?: string;
  nickname?: string;
  gender?: boolean;
  adventureInventoryCharId?: string;
  adventureCharacteristicId?: string;
  adventureRaceId?: string;
  adventureSkills?: IAdventureSkill[];
}

export class AdventureAccountCharacter implements IAdventureAccountCharacter {
  constructor(
    public id?: string,
    public nickname?: string,
    public gender?: boolean,
    public adventureInventoryCharId?: string,
    public adventureCharacteristicId?: string,
    public adventureRaceId?: string,
    public adventureSkills?: IAdventureSkill[]
  ) {
    this.gender = this.gender || false;
  }
}
