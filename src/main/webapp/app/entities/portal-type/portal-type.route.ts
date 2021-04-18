import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PortalType } from 'app/shared/model/portal-type.model';
import { PortalTypeService } from './portal-type.service';
import { PortalTypeComponent } from './portal-type.component';
import { PortalTypeDetailComponent } from './portal-type-detail.component';
import { PortalTypeUpdateComponent } from './portal-type-update.component';
import { PortalTypeDeletePopupComponent } from './portal-type-delete-dialog.component';
import { IPortalType } from 'app/shared/model/portal-type.model';

@Injectable({ providedIn: 'root' })
export class PortalTypeResolve implements Resolve<IPortalType> {
    constructor(private service: PortalTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((portalType: HttpResponse<PortalType>) => portalType.body));
        }
        return of(new PortalType());
    }
}

export const portalTypeRoute: Routes = [
    {
        path: 'portal-type',
        component: PortalTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'riscScraperAdmApp.portalType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'portal-type/:id/view',
        component: PortalTypeDetailComponent,
        resolve: {
            portalType: PortalTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.portalType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'portal-type/new',
        component: PortalTypeUpdateComponent,
        resolve: {
            portalType: PortalTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.portalType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'portal-type/:id/edit',
        component: PortalTypeUpdateComponent,
        resolve: {
            portalType: PortalTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.portalType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const portalTypePopupRoute: Routes = [
    {
        path: 'portal-type/:id/delete',
        component: PortalTypeDeletePopupComponent,
        resolve: {
            portalType: PortalTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.portalType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
