export interface IAdventureInventoryChar {
    id?: number;
    adventureItemId?: number;
}

export class AdventureInventoryChar implements IAdventureInventoryChar {
    constructor(public id?: number, public adventureItemId?: number) {}
}
