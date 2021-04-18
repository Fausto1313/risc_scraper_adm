import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RiscScraperAdmSharedModule } from 'app/shared';
import {
    SchedulerComponent,
    SchedulerDetailComponent,
    SchedulerUpdateComponent,
    SchedulerDeletePopupComponent,
    SchedulerDeleteDialogComponent,
    schedulerRoute,
    schedulerPopupRoute
} from './';

const ENTITY_STATES = [...schedulerRoute, ...schedulerPopupRoute];

@NgModule({
    imports: [RiscScraperAdmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SchedulerComponent,
        SchedulerDetailComponent,
        SchedulerUpdateComponent,
        SchedulerDeleteDialogComponent,
        SchedulerDeletePopupComponent
    ],
    entryComponents: [SchedulerComponent, SchedulerUpdateComponent, SchedulerDeleteDialogComponent, SchedulerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RiscScraperAdmSchedulerModule {}
