export interface IAdventureModelOptions {
    id?: number;
    color?: string;
}

export class AdventureModelOptions implements IAdventureModelOptions {
    constructor(public id?: number, public color?: string) {}
}
