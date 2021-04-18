import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RiscScraperAdmSharedModule } from 'app/shared';
import {
    PortalTypeComponent,
    PortalTypeDetailComponent,
    PortalTypeUpdateComponent,
    PortalTypeDeletePopupComponent,
    PortalTypeDeleteDialogComponent,
    portalTypeRoute,
    portalTypePopupRoute
} from './';

const ENTITY_STATES = [...portalTypeRoute, ...portalTypePopupRoute];

@NgModule({
    imports: [RiscScraperAdmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PortalTypeComponent,
        PortalTypeDetailComponent,
        PortalTypeUpdateComponent,
        PortalTypeDeleteDialogComponent,
        PortalTypeDeletePopupComponent
    ],
    entryComponents: [PortalTypeComponent, PortalTypeUpdateComponent, PortalTypeDeleteDialogComponent, PortalTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RiscScraperAdmPortalTypeModule {}
