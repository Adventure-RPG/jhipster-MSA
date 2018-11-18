export interface IAdventureFraction {
    id?: number;
    name?: string;
    desc?: string;
    imageContentType?: string;
    image?: any;
}

export class AdventureFraction implements IAdventureFraction {
    constructor(public id?: number, public name?: string, public desc?: string, public imageContentType?: string, public image?: any) {}
}
