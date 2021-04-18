import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPages } from 'app/shared/model/pages.model';

type EntityResponseType = HttpResponse<IPages>;
type EntityArrayResponseType = HttpResponse<IPages[]>;

@Injectable({ providedIn: 'root' })
export class PagesService {
    public resourceUrl = SERVER_API_URL + 'api/pages';

    constructor(private http: HttpClient) {}

    create(pages: IPages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pages);
        return this.http
            .post<IPages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pages: IPages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pages);
        return this.http
            .put<IPages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPages>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPages[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pages: IPages): IPages {
        const copy: IPages = Object.assign({}, pages, {
            date: pages.date != null && pages.date.isValid() ? pages.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pages: IPages) => {
            pages.date = pages.date != null ? moment(pages.date) : null;
        });
        return res;
    }
}
