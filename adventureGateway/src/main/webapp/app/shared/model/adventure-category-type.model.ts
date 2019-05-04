export interface IAdventureCategoryType {
  id?: string;
  name?: string;
  desc?: string;
}

export class AdventureCategoryType implements IAdventureCategoryType {
  constructor(public id?: string, public name?: string, public desc?: string) {}
}
