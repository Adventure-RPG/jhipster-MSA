export interface IAdventureCategoryType {
    id?: number;
    name?: string;
    desc?: string;
}

export class AdventureCategoryType implements IAdventureCategoryType {
    constructor(public id?: number, public name?: string, public desc?: string) {}
}
