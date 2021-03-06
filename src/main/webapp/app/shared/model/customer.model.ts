import { IUser } from 'app/core/user/user.model';
import { IPortal } from 'app/shared/model//portal.model';

export interface ICustomer {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    phone?: string;
    addressLine1?: string;
    addressLine2?: string;
    city?: string;
    country?: string;
    user?: IUser;
    portals?: IPortal[];
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phone?: string,
        public addressLine1?: string,
        public addressLine2?: string,
        public city?: string,
        public country?: string,
        public user?: IUser,
        public portals?: IPortal[]
    ) {}
}
