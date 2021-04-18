import { Moment } from 'moment';
import { IPortal } from 'app/shared/model//portal.model';

export interface IPages {
    id?: number;
    idscrapyData?: number;
    headerData?: any;
    bodyData?: any;
    autorData?: any;
    dateData?: any;
    headerClean?: any;
    bodyClean?: any;
    autorClean?: any;
    dateClean?: any;
    resumeClean?: any;
    resumeData?: any;
    screenshotContentType?: string;
    screenshot?: any;
    date?: Moment;
    fullPath?: string;
    url?: string;
    portal?: IPortal;
}

export class Pages implements IPages {
    constructor(
        public id?: number,
        public idscrapyData?: number,
        public headerData?: any,
        public bodyData?: any,
        public autorData?: any,
        public dateData?: any,
        public headerClean?: any,
        public bodyClean?: any,
        public autorClean?: any,
        public dateClean?: any,
        public resumeClean?: any,
        public resumeData?: any,
        public screenshotContentType?: string,
        public screenshot?: any,
        public date?: Moment,
        public fullPath?: string,
        public url?: string,
        public portal?: IPortal
    ) {}
}
