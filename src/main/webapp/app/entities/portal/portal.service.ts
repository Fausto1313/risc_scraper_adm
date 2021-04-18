import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPortal } from 'app/shared/model/portal.model';

type EntityResponseType = HttpResponse<IPortal>;
type EntityArrayResponseType = HttpResponse<IPortal[]>;

@Injectable({ providedIn: 'root' })
export class PortalService {
    public resourceUrl = SERVER_API_URL + 'api/portals';

    constructor(private http: HttpClient) {}

    create(portal: IPortal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(portal);
        return this.http
            .post<IPortal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(portal: IPortal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(portal);
        return this.http
            .put<IPortal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPortal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPortal[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(portal: IPortal): IPortal {
        const copy: IPortal = Object.assign({}, portal, {
            fecha: portal.fecha != null && portal.fecha.isValid() ? portal.fecha.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((portal: IPortal) => {
            portal.fecha = portal.fecha != null ? moment(portal.fecha) : null;
        });
        return res;
    }
}
