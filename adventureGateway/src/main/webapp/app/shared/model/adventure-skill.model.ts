import { IAdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';

export interface IAdventureSkill {
    id?: string;
    name?: string;
    position?: boolean;
    adventureScriptId?: string;
    adventureAccountCharacters?: IAdventureAccountCharacter[];
}

export class AdventureSkill implements IAdventureSkill {
    constructor(
        public id?: string,
        public name?: string,
        public position?: boolean,
        public adventureScriptId?: string,
        public adventureAccountCharacters?: IAdventureAccountCharacter[]
    ) {
        this.position = this.position || false;
    }
}
