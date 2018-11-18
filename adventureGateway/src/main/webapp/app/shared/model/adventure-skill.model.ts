export interface IAdventureSkill {
    id?: number;
    name?: string;
    position?: boolean;
    adventureScriptId?: number;
}

export class AdventureSkill implements IAdventureSkill {
    constructor(public id?: number, public name?: string, public position?: boolean, public adventureScriptId?: number) {
        this.position = this.position || false;
    }
}
