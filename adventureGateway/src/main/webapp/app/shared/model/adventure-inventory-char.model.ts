export interface IAdventureInventoryChar {
  id?: string;
  adventureItemId?: string;
}

export class AdventureInventoryChar implements IAdventureInventoryChar {
  constructor(public id?: string, public adventureItemId?: string) {}
}
