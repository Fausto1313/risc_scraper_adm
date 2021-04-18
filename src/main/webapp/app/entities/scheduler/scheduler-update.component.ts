import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IScheduler } from 'app/shared/model/scheduler.model';
import { SchedulerService } from './scheduler.service';

@Component({
    selector: 'jhi-scheduler-update',
    templateUrl: './scheduler-update.component.html'
})
export class SchedulerUpdateComponent implements OnInit {
    scheduler: IScheduler;
    isSaving: boolean;
    timestamp: string;

    constructor(private schedulerService: SchedulerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ scheduler }) => {
            this.scheduler = scheduler;
            this.timestamp = this.scheduler.timestamp != null ? this.scheduler.timestamp.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.scheduler.timestamp = this.timestamp != null ? moment(this.timestamp, DATE_TIME_FORMAT) : null;
        if (this.scheduler.id !== undefined) {
            this.subscribeToSaveResponse(this.schedulerService.update(this.scheduler));
        } else {
            this.subscribeToSaveResponse(this.schedulerService.create(this.scheduler));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IScheduler>>) {
        result.subscribe((res: HttpResponse<IScheduler>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
