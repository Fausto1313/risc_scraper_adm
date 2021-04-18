import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScheduler } from 'app/shared/model/scheduler.model';

@Component({
    selector: 'jhi-scheduler-detail',
    templateUrl: './scheduler-detail.component.html'
})
export class SchedulerDetailComponent implements OnInit {
    scheduler: IScheduler;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ scheduler }) => {
            this.scheduler = scheduler;
        });
    }

    previousState() {
        window.history.back();
    }
}
