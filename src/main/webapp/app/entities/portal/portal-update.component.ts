import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPortal } from 'app/shared/model/portal.model';
import { PortalService } from './portal.service';
import { IScheduler } from 'app/shared/model/scheduler.model';
import { SchedulerService } from 'app/entities/scheduler';
import { IPortalType } from 'app/shared/model/portal-type.model';
import { PortalTypeService } from 'app/entities/portal-type';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
    selector: 'jhi-portal-update',
    templateUrl: './portal-update.component.html'
})
export class PortalUpdateComponent implements OnInit {
    portal: IPortal;
    isSaving: boolean;

    schedulers: IScheduler[];

    portaltypes: IPortalType[];

    customers: ICustomer[];
    fecha: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private portalService: PortalService,
        private schedulerService: SchedulerService,
        private portalTypeService: PortalTypeService,
        private customerService: CustomerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ portal }) => {
            this.portal = portal;
            this.fecha = this.portal.fecha != null ? this.portal.fecha.format(DATE_TIME_FORMAT) : null;
        });
        this.schedulerService.query({ filter: 'portal-is-null' }).subscribe(
            (res: HttpResponse<IScheduler[]>) => {
                if (!this.portal.scheduler || !this.portal.scheduler.id) {
                    this.schedulers = res.body;
                } else {
                    this.schedulerService.find(this.portal.scheduler.id).subscribe(
                        (subRes: HttpResponse<IScheduler>) => {
                            this.schedulers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.portalTypeService.query({ filter: 'portal-is-null' }).subscribe(
            (res: HttpResponse<IPortalType[]>) => {
                if (!this.portal.portalType || !this.portal.portalType.id) {
                    this.portaltypes = res.body;
                } else {
                    this.portalTypeService.find(this.portal.portalType.id).subscribe(
                        (subRes: HttpResponse<IPortalType>) => {
                            this.portaltypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.customerService.query().subscribe(
            (res: HttpResponse<ICustomer[]>) => {
                this.customers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.portal.fecha = this.fecha != null ? moment(this.fecha, DATE_TIME_FORMAT) : null;
        if (this.portal.id !== undefined) {
            this.subscribeToSaveResponse(this.portalService.update(this.portal));
        } else {
            this.subscribeToSaveResponse(this.portalService.create(this.portal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPortal>>) {
        result.subscribe((res: HttpResponse<IPortal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSchedulerById(index: number, item: IScheduler) {
        return item.id;
    }

    trackPortalTypeById(index: number, item: IPortalType) {
        return item.id;
    }

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }
}
