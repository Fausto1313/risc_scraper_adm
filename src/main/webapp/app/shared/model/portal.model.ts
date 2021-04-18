import { Moment } from 'moment';
import { IScheduler } from 'app/shared/model//scheduler.model';
import { IPortalType } from 'app/shared/model//portal-type.model';
import { IPages } from 'app/shared/model//pages.model';
import { ICustomer } from 'app/shared/model//customer.model';

export interface IPortal {
    id?: number;
    idscrapyData?: number;
    name?: string;
    domain?: string;
    url?: string;
    headerPath?: string;
    bodyPath?: string;
    autorPath?: string;
    datePath?: string;
    resumePath?: string;
    path?: string;
    xpath?: boolean;
    identifier?: string;
    usuario?: string;
    fecha?: Moment;
    scheduler?: IScheduler;
    portalType?: IPortalType;
    pages?: IPages[];
    customer?: ICustomer;
}

export class Portal implements IPortal {
    constructor(
        public id?: number,
        public idscrapyData?: number,
        public name?: string,
        public domain?: string,
        public url?: string,
        public headerPath?: string,
        public bodyPath?: string,
        public autorPath?: string,
        public datePath?: string,
        public resumePath?: string,
        public path?: string,
        public xpath?: boolean,
        public identifier?: string,
        public usuario?: string,
        public fecha?: Moment,
        public scheduler?: IScheduler,
        public portalType?: IPortalType,
        public pages?: IPages[],
        public customer?: ICustomer
    ) {
        this.xpath = this.xpath || false;
    }
}
