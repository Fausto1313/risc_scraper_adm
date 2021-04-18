import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Scheduler } from 'app/shared/model/scheduler.model';
import { SchedulerService } from './scheduler.service';
import { SchedulerComponent } from './scheduler.component';
import { SchedulerDetailComponent } from './scheduler-detail.component';
import { SchedulerUpdateComponent } from './scheduler-update.component';
import { SchedulerDeletePopupComponent } from './scheduler-delete-dialog.component';
import { IScheduler } from 'app/shared/model/scheduler.model';

@Injectable({ providedIn: 'root' })
export class SchedulerResolve implements Resolve<IScheduler> {
    constructor(private service: SchedulerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((scheduler: HttpResponse<Scheduler>) => scheduler.body));
        }
        return of(new Scheduler());
    }
}

export const schedulerRoute: Routes = [
    {
        path: 'scheduler',
        component: SchedulerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'riscScraperAdmApp.scheduler.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'scheduler/:id/view',
        component: SchedulerDetailComponent,
        resolve: {
            scheduler: SchedulerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.scheduler.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'scheduler/new',
        component: SchedulerUpdateComponent,
        resolve: {
            scheduler: SchedulerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.scheduler.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'scheduler/:id/edit',
        component: SchedulerUpdateComponent,
        resolve: {
            scheduler: SchedulerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.scheduler.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schedulerPopupRoute: Routes = [
    {
        path: 'scheduler/:id/delete',
        component: SchedulerDeletePopupComponent,
        resolve: {
            scheduler: SchedulerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'riscScraperAdmApp.scheduler.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
