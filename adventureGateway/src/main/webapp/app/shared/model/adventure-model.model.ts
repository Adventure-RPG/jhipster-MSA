export interface IAdventureModel {
    id?: number;
    name?: string;
    fileContentType?: string;
    file?: any;
    adventureCategoryTypeId?: number;
}

export class AdventureModel implements IAdventureModel {
    constructor(
        public id?: number,
        public name?: string,
        public fileContentType?: string,
        public file?: any,
        public adventureCategoryTypeId?: number
    ) {}
}
