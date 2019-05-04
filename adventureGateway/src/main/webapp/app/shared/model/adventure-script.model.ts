export interface IAdventureScript {
  id?: string;
  name?: string;
  fileContentType?: string;
  file?: any;
  argumentsScript?: string;
}

export class AdventureScript implements IAdventureScript {
  constructor(
    public id?: string,
    public name?: string,
    public fileContentType?: string,
    public file?: any,
    public argumentsScript?: string
  ) {}
}
