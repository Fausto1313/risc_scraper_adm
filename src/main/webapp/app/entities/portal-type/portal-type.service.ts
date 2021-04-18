import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPortalType } from 'app/shared/model/portal-type.model';

type EntityResponseType = HttpResponse<IPortalType>;
type EntityArrayResponseType = HttpResponse<IPortalType[]>;

@Injectable({ providedIn: 'root' })
export class PortalTypeService {
    public resourceUrl = SERVER_API_URL + 'api/portal-types';

    constructor(private http: HttpClient) {}

    create(portalType: IPortalType): Observable<EntityResponseType> {
        return this.http.post<IPortalType>(this.resourceUrl, portalType, { observe: 'response' });
    }

    update(portalType: IPortalType): Observable<EntityResponseType> {
        return this.http.put<IPortalType>(this.resourceUrl, portalType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPortalType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPortalType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
