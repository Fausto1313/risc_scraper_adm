import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IScheduler } from 'app/shared/model/scheduler.model';

type EntityResponseType = HttpResponse<IScheduler>;
type EntityArrayResponseType = HttpResponse<IScheduler[]>;

@Injectable({ providedIn: 'root' })
export class SchedulerService {
    public resourceUrl = SERVER_API_URL + 'api/schedulers';

    constructor(private http: HttpClient) {}

    create(scheduler: IScheduler): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(scheduler);
        return this.http
            .post<IScheduler>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(scheduler: IScheduler): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(scheduler);
        return this.http
            .put<IScheduler>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IScheduler>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IScheduler[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(scheduler: IScheduler): IScheduler {
        const copy: IScheduler = Object.assign({}, scheduler, {
            timestamp: scheduler.timestamp != null && scheduler.timestamp.isValid() ? scheduler.timestamp.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.timestamp = res.body.timestamp != null ? moment(res.body.timestamp) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((scheduler: IScheduler) => {
            scheduler.timestamp = scheduler.timestamp != null ? moment(scheduler.timestamp) : null;
        });
        return res;
    }
}
