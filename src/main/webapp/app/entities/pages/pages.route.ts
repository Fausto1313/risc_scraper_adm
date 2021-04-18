import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pages } from 'app/shared/model/pages.model';
import { PagesService } from './pages.service';
import { PagesComponent } from './pages.component';
import { PagesDetailComponent } from './pages-detail.component';
import { PagesUpdateComponent } from './pages-update.component';
import { PagesDeletePopupComponent } from './pages-delete-dialog.component';
import { IPages } from 'app/shared/model/pages.model';

@Injectable({ providedIn: 'root' })
export class PagesResolve implements Resolve<IPages> {
    constructor(private service: PagesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pages: HttpResponse<Pages>) => pages.body));
        }
        return of(new Pages());
    }
}

export const pagesRoute: Routes = [
    {
        path: 'pages',
        component: PagesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'riscScraperAdmApp.pages.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pages/:id/view',
        component: PagesDetailComponent,
        resolve: {
            pages: PagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.pages.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pages/new',
        component: PagesUpdateComponent,
        resolve: {
            pages: PagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.pages.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pages/:id/edit',
        component: PagesUpdateComponent,
        resolve: {
            pages: PagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.pages.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pagesPopupRoute: Routes = [
    {
        path: 'pages/:id/delete',
        component: PagesDeletePopupComponent,
        resolve: {
            pages: PagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.pages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
