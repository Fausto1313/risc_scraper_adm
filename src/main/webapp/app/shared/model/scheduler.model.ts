import { Moment } from 'moment';

export interface IScheduler {
    id?: number;
    identifier?: string;
    status?: number;
    actor?: number;
    schedule?: string;
    project?: string;
    spider?: string;
    timestamp?: Moment;
    duration?: number;
    jobidentifier?: string;
}

export class Scheduler implements IScheduler {
    constructor(
        public id?: number,
        public identifier?: string,
        public status?: number,
        public actor?: number,
        public schedule?: string,
        public project?: string,
        public spider?: string,
        public timestamp?: Moment,
        public duration?: number,
        public jobidentifier?: string
    ) {}
}
