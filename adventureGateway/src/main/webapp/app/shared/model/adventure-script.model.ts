export interface IAdventureScript {
    id?: number;
    name?: string;
    fileContentType?: string;
    file?: any;
    argumentsScript?: string;
}

export class AdventureScript implements IAdventureScript {
    constructor(
        public id?: number,
        public name?: string,
        public fileContentType?: string,
        public file?: any,
        public argumentsScript?: string
    ) {}
}
