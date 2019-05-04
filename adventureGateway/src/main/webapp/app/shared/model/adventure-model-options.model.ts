export interface IAdventureModelOptions {
  id?: string;
  color?: string;
}

export class AdventureModelOptions implements IAdventureModelOptions {
  constructor(public id?: string, public color?: string) {}
}
