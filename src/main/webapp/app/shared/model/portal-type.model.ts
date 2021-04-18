export interface IPortalType {
    id?: number;
    name?: string;
    description?: string;
}

export class PortalType implements IPortalType {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
